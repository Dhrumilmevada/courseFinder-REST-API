package org.dhrumil.course.coursefinder.udemy.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.bson.Document;
import org.dhrumil.course.coursefinder.udemy.model.Course;
import org.dhrumil.course.coursefinder.utils.DateUtils;
import org.dhrumil.course.coursefinder.utils.JsonUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

public class UdemyCourseService {

  public static final String COURSE_COLLECTION = "courses";
  public static final String DATABASE_NAME = "udemy";
  private MongoClient dbClient = null;
  private MongoDatabase udemyDB = null;
  private MongoCollection<Document> collection = null;

  private static enum CourseLength {
    EXTRA_SHORT(1), SHORT(2), MEDIUM(3), LONG(4), EXTRA_LONG(5);

    private int value;
    private static Map<Integer, CourseLength> map = new HashMap<Integer, CourseLength>();

    private CourseLength(int value) {
      this.value = value;
    }

    static {
      for (CourseLength length : CourseLength.values()) {
        map.put(length.value, length);
      }
    }

    public static CourseLength valueOf(int value) {
      return (CourseLength) map.get(value);
    }

    public int getValue() {
      return value;
    }
  }

  public UdemyCourseService() {
    dbClient =
        new MongoClient(System.getenv("MONGO_HOST"), Integer.parseInt(System.getenv("MONGO_PORT")));
    udemyDB = dbClient.getDatabase(DATABASE_NAME);
    collection = udemyDB.getCollection(COURSE_COLLECTION);
    collection.createIndex(Indexes.text("metadata"));

  }


  public List<Course> getCourseForTopic(String topic) {
    List<Course> courseList = new ArrayList<Course>();

    MongoCursor<Document> cursor =
        collection.find(new Document("$text", new Document("$search", topic))).iterator();

    while (cursor.hasNext()) {
      Document doc = cursor.next();
      String courserStr = doc.toJson();
      Course course = JsonUtils.stringToObject(courserStr, Course.class);
      courseList.add(course);
    }

    return courseList;
  }

  public Long getCourseCountForTopic(String topic) {
    Long count = collection.countDocuments(new Document("$text", new Document("$search", topic)));
    return count;
  }

  public List<Course> getMostEnrolledCourseForTopic(String topic) {
    List<Course> courseList = getCourseForTopic(topic);

    List<Course> sortedList = courseList.stream().sorted(new Comparator<Course>() {

      @Override
      public int compare(Course o1, Course o2) {
        return o2.getSubscriberCount() - o1.getSubscriberCount();
      }
    }).collect(Collectors.toList());

    return sortedList;
  }

  public List<Course> getMostReviewedCourseForTopic(String topic) {
    List<Course> courseList = getCourseForTopic(topic);

    List<Course> sortedList = courseList.stream().sorted(new Comparator<Course>() {

      @Override
      public int compare(Course o1, Course o2) {
        return o2.getReviewCount() - o1.getReviewCount();
      }
    }).collect(Collectors.toList());

    return sortedList;
  }

  public List<Course> getMosRatedCourseForTopic(String topic) {
    List<Course> courseList = getCourseForTopic(topic);

    List<Course> sortedList = courseList.stream().sorted(new Comparator<Course>() {

      @Override
      public int compare(Course o1, Course o2) {
        if ((o2.getRating() - o1.getRating()) < 0.0) {
          return -1;
        } else if ((o2.getRating() - o1.getRating()) > 0.0) {
          return 1;
        } else {
          return 0;
        }
      }
    }).collect(Collectors.toList());

    return sortedList;
  }

  public List<Course> getMostRelevantCoursesForTopic(String topic) {
    List<Course> courseList = getCourseForTopic(topic);
    List<Course> mostRelevantCourse = courseList.stream()
        .filter(course -> course.getTitle().toLowerCase().contains(topic.toLowerCase()))
        .sorted(new Comparator<Course>() {

          @Override
          public int compare(Course o1, Course o2) {
            if ((o2.getReviewCount() - o1.getReviewCount()) < 0) {
              return -1;
            } else if ((o2.getReviewCount() - o1.getReviewCount()) > 0) {
              return 1;
            } else {
              return 0;
            }
          }
        }).collect(Collectors.toList());
    return mostRelevantCourse;
  }

  public List<Course> getLowPriceCoursesForTopic(String topic) {
    List<Course> courseList = getCourseForTopic(topic);
    List<Course> sortedList =
        courseList.stream().filter(course -> course.isPaid()).sorted(new Comparator<Course>() {

          @Override
          public int compare(Course o1, Course o2) {
            if ((o1.isDiscountAvailable() && DateUtils
                .isCurrentDateInBetween(o1.getDiscountStartTime(), o1.getDiscountEndTime()))
                && (o2.isDiscountAvailable() && DateUtils
                    .isCurrentDateInBetween(o2.getDiscountStartTime(), o2.getDiscountEndTime()))) {
              if (o1.getDiscountedPrice() - o2.getDiscountedPrice() == 0) {
                if ((o2.getRating() - o1.getRating()) < 0.0) {
                  return -1;
                } else if ((o2.getRating() - o1.getRating()) > 0.0) {
                  return 1;
                } else {
                  return 0;
                }
              }
              return o1.getDiscountedPrice() - o2.getDiscountedPrice();
            } else if ((o1.isDiscountAvailable() && DateUtils.isCurrentDateInBetween(
                o1.getDiscountStartTime(), o1.getDiscountEndTime()) && !o2.isDiscountAvailable())) {
              if (o1.getDiscountedPrice() - o2.getAmount() == 0) {
                if ((o2.getRating() - o1.getRating()) < 0.0) {
                  return -1;
                } else if ((o2.getRating() - o1.getRating()) > 0.0) {
                  return 1;
                } else {
                  return 0;
                }
              }
              return o1.getDiscountedPrice() - o2.getAmount();

            } else if (!o1.isDiscountAvailable() && (o2.isDiscountAvailable() && DateUtils
                .isCurrentDateInBetween(o2.getDiscountStartTime(), o2.getDiscountEndTime()))) {
              if (o1.getAmount() - o2.getDiscountedPrice() == 0) {
                if ((o2.getRating() - o1.getRating()) < 0.0) {
                  return -1;
                } else if ((o2.getRating() - o1.getRating()) > 0.0) {
                  return 1;
                } else {
                  return 0;
                }
              }
              return o1.getAmount() - o2.getDiscountedPrice();

            } else {
              if (o1.getAmount() - o2.getAmount() == 0) {
                if ((o2.getRating() - o1.getRating()) < 0.0) {
                  return -1;
                } else if ((o2.getRating() - o1.getRating()) > 0.0) {
                  return 1;
                } else {
                  return 0;
                }
              }
              return o1.getAmount() - o2.getAmount();
            }
          }
        }).collect(Collectors.toList());
    return sortedList;
  }

  public List<Course> getHighPriceCoursesForTopic(String topic) {
    List<Course> courseList = getCourseForTopic(topic);
    List<Course> sortedList =
        courseList.stream().filter(course -> course.isPaid()).sorted(new Comparator<Course>() {

          @Override
          public int compare(Course o1, Course o2) {
            if ((o1.isDiscountAvailable() && DateUtils
                .isCurrentDateInBetween(o1.getDiscountStartTime(), o1.getDiscountEndTime()))
                && (o2.isDiscountAvailable() && DateUtils
                    .isCurrentDateInBetween(o2.getDiscountStartTime(), o2.getDiscountEndTime()))) {
              if (o1.getDiscountedPrice() - o2.getDiscountedPrice() == 0) {
                if ((o2.getRating() - o1.getRating()) < 0.0) {
                  return -1;
                } else if ((o2.getRating() - o1.getRating()) > 0.0) {
                  return 1;
                } else {
                  return 0;
                }
              }
              return o2.getDiscountedPrice() - o1.getDiscountedPrice();
            } else if ((o1.isDiscountAvailable() && DateUtils.isCurrentDateInBetween(
                o1.getDiscountStartTime(), o1.getDiscountEndTime()) && !o2.isDiscountAvailable())) {
              if (o1.getDiscountedPrice() - o2.getAmount() == 0) {
                if ((o2.getRating() - o1.getRating()) < 0.0) {
                  return -1;
                } else if ((o2.getRating() - o1.getRating()) > 0.0) {
                  return 1;
                } else {
                  return 0;
                }
              }
              return o2.getDiscountedPrice() - o1.getAmount();

            } else if (!o1.isDiscountAvailable() && (o2.isDiscountAvailable() && DateUtils
                .isCurrentDateInBetween(o2.getDiscountStartTime(), o2.getDiscountEndTime()))) {
              if (o1.getAmount() - o2.getDiscountedPrice() == 0) {
                if ((o2.getRating() - o1.getRating()) < 0.0) {
                  return -1;
                } else if ((o2.getRating() - o1.getRating()) > 0.0) {
                  return 1;
                } else {
                  return 0;
                }
              }
              return o2.getAmount() - o1.getDiscountedPrice();

            } else {
              if (o1.getAmount() - o2.getAmount() == 0) {
                if ((o2.getRating() - o1.getRating()) < 0.0) {
                  return -1;
                } else if ((o2.getRating() - o1.getRating()) > 0.0) {
                  return 1;
                } else {
                  return 0;
                }
              }
              return o2.getAmount() - o1.getAmount();
            }
          }
        }).collect(Collectors.toList());
    return sortedList;
  }

  public List<Course> getFreeCoursesForTopic(String topic) {
    List<Course> courseList = getCourseForTopic(topic);
    List<Course> sortedList =
        courseList.stream().filter(course -> !course.isPaid()).sorted(new Comparator<Course>() {

          @Override
          public int compare(Course o1, Course o2) {
            if ((o2.getReviewCount() - o1.getReviewCount()) < 0) {
              return -1;
            } else {
              return 1;
            }
          }

        }).collect(Collectors.toList());
    return sortedList;
  }

  public List<Course> getDiscountedCoursesForTopic(String topic) {
    List<Course> courseList = getCourseForTopic(topic);
    List<Course> sortedList = courseList.stream().filter(new Predicate<Course>() {

      @Override
      public boolean test(Course course) {
        return course.isDiscountAvailable() && DateUtils
            .isCurrentDateInBetween(course.getDiscountStartTime(), course.getDiscountEndTime());
      }
    }).sorted(new Comparator<Course>() {

      @Override
      public int compare(Course o1, Course o2) {
        if ((o2.getReviewCount() - o1.getReviewCount()) < 0) {
          return -1;
        } else {
          return 1;
        }
      }

    }).collect(Collectors.toList());
    return sortedList;
  }

  public List<Course> getTrendingCoursesForTopic(String topic) {
    List<Course> courseList = getCourseForTopic(topic);

    List<Course> mostReviewRecently = courseList.stream().sorted(new Comparator<Course>() {

      @Override
      public int compare(Course o1, Course o2) {
        return o2.getReviewCount_recent() - o1.getReviewCount_recent();
      }
    }).collect(Collectors.toList());

    List<Course> topMostReviewedRecently;

    if (mostReviewRecently.size() > 10) {
      topMostReviewedRecently = mostReviewRecently.subList(0, 10);
    } else {
      topMostReviewedRecently = mostReviewRecently;
    }

    return topMostReviewedRecently;
  }

  public List<Course> getCoursesbasedOnRatingForTopic(String search, double rating) {
    List<Course> courseList = getCourseForTopic(search);

    List<Course> ratedFilteredCourses = courseList.stream().filter(new Predicate<Course>() {

      @Override
      public boolean test(Course course) {

        return course.getRating() >= rating;
      }
    }).sorted(new Comparator<Course>() {

      @Override
      public int compare(Course o1, Course o2) {
        return o2.getReviewCount() - o1.getReviewCount();
      }
    }).collect(Collectors.toList());

    return ratedFilteredCourses;
  }

  public List<Course> getCoursesbasedOnLenthForTopic(String search, String length) {
    List<Course> courseList = getCourseForTopic(search);

    String[] courseDurationSearch = length.trim().split(",");

    if (courseDurationSearch.length == 1) {
      List<Course> filteredCourseList = getCoursebasedOnDuration(courseList, length);
      filteredCourseList.sort(new Comparator<Course>() {

        @Override
        public int compare(Course o1, Course o2) {
          if ((o2.getReviewCount() - o1.getReviewCount()) < 0.0) {
            return -1;
          } else if ((o2.getReviewCount() - o1.getReviewCount()) > 0.0) {
            return 1;
          } else {
            return 0;
          }
        }
      });
      return filteredCourseList;
    } else {
      List<Course> multiLengthFilteredCourses = new ArrayList<Course>();
      for (String duration : courseDurationSearch) {
        List<Course> filtredCourses = getCoursebasedOnDuration(courseList, duration);
        multiLengthFilteredCourses.addAll(filtredCourses);
      }
      multiLengthFilteredCourses.sort(new Comparator<Course>() {

        @Override
        public int compare(Course o1, Course o2) {
          if ((o2.getReviewCount() - o1.getReviewCount()) < 0.0) {
            return -1;
          } else if ((o2.getReviewCount() - o1.getReviewCount()) > 0.0) {
            return 1;
          } else {
            return 0;
          }
        }
      });
      return multiLengthFilteredCourses;
    }
  }

  private List<Course> getCoursebasedOnDuration(List<Course> courses, String duration) {
    List<Course> singleLengthFilteredCourses = courses.stream().filter(new Predicate<Course>() {

      @Override
      public boolean test(Course course) {
        if (duration.equalsIgnoreCase(CourseLength.EXTRA_SHORT.toString())) {
          return TimeUnit.SECONDS.toHours((long) course.getContentLength()) >= 0
              && TimeUnit.SECONDS.toHours((long) course.getContentLength()) < 2;
        } else if (duration.equalsIgnoreCase(CourseLength.SHORT.toString())) {
          return TimeUnit.SECONDS.toHours((long) course.getContentLength()) >= 2
              && TimeUnit.SECONDS.toHours((long) course.getContentLength()) < 5;
        } else if (duration.equalsIgnoreCase(CourseLength.MEDIUM.toString())) {
          return TimeUnit.SECONDS.toHours((long) course.getContentLength()) >= 5
              && TimeUnit.SECONDS.toHours((long) course.getContentLength()) < 10;
        } else if (duration.equalsIgnoreCase(CourseLength.LONG.toString())) {
          return TimeUnit.SECONDS.toHours((long) course.getContentLength()) >= 10
              && TimeUnit.SECONDS.toHours((long) course.getContentLength()) < 20;
        } else if (duration.equalsIgnoreCase(CourseLength.EXTRA_LONG.toString())) {
          return TimeUnit.SECONDS.toHours((long) course.getContentLength()) >= 20;
        } else {
          return false;
        }
      }
    }).collect(Collectors.toList());
    return singleLengthFilteredCourses;
  }

  public List<Course> getNewestCourse(String search) {
    List<Course> courseList = getCourseForTopic(search);
    
    List<Course> newestCourses = courseList.stream().filter(new Predicate<Course>() {

      @Override
      public boolean test(Course course) {
        return DateUtils.checkIfDateIsInLastThreeMonth(course.getPublishedOn());
      }
    }).sorted(new Comparator<Course>() {

      @Override
      public int compare(Course o1, Course o2) {
        return o2.getReviewCount() - o1.getReviewCount();
      }
    }).collect(Collectors.toList());
    return newestCourses;
  }
}
