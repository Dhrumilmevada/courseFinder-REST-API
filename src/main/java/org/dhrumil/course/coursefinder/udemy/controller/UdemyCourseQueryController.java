package org.dhrumil.course.coursefinder.udemy.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.dhrumil.course.coursefinder.udemy.model.Course;
import org.dhrumil.course.coursefinder.udemy.service.UdemyCourseService;

@Path("topic")
public class UdemyCourseQueryController {

  private UdemyCourseService service = new UdemyCourseService();

  @GET
  @Path("{search}/coursecount")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCourseCount(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }
    Long courseCount = service.getCourseCountForTopic(search);

    if (courseCount == null) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseCount)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getCourseForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;

  }

  @GET
  @Path("{search}/mostEnrolled")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMostEnrolledCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getMostEnrolledCourseForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/mostEnrolled/top")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMostEnrolledTopCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getMostEnrolledCourseForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> topCourseList;
    if (courseList.size() >= 10) {
      topCourseList = courseList.subList(0, 10);
    } else {
      topCourseList = courseList;
    }

    Response res = Response.status(Response.Status.OK).entity(topCourseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/mostReviewed")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMostReviewedCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getMostReviewedCourseForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/mostReviewed/top")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMostReviewedTopCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getMostReviewedCourseForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> topCourseList;
    if (courseList.size() >= 10) {
      topCourseList = courseList.subList(0, 10);
    } else {
      topCourseList = courseList;
    }

    Response res = Response.status(Response.Status.OK).entity(topCourseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/mostRated")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMostRatedCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getMosRatedCourseForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/mostRated/top")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMostRatedTopCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getMosRatedCourseForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> topCourseList;
    if (courseList.size() >= 10) {
      topCourseList = courseList.subList(0, 10);
    } else {
      topCourseList = courseList;
    }

    Response res = Response.status(Response.Status.OK).entity(topCourseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/mostRelevant")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMostRelevantCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getMostRelevantCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/mostRelevant/top")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMostRelevantTopCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getMostRelevantCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> topCourseList;
    if (courseList.size() >= 10) {
      topCourseList = courseList.subList(0, 10);
    } else {
      topCourseList = courseList;
    }

    Response res = Response.status(Response.Status.OK).entity(topCourseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }


  @GET
  @Path("{search}/lowPriced")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getLowPricedCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getLowPriceCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/lowPriced/top")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getLowPricedTopCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getLowPriceCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> topCourseList;
    if (courseList.size() >= 10) {
      topCourseList = courseList.subList(0, 10);
    } else {
      topCourseList = courseList;
    }

    Response res = Response.status(Response.Status.OK).entity(topCourseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/highPriced")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getHighPricedCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getHighPriceCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/highPriced/top")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getHighPricedTopCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getHighPriceCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> topCourseList;
    if (courseList.size() >= 10) {
      topCourseList = courseList.subList(0, 10);
    } else {
      topCourseList = courseList;
    }

    Response res = Response.status(Response.Status.OK).entity(topCourseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/free")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getFreeCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getFreeCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/free/top")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getFreeTopCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getFreeCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> topCourseList;
    if (courseList.size() >= 10) {
      topCourseList = courseList.subList(0, 10);
    } else {
      topCourseList = courseList;
    }

    Response res = Response.status(Response.Status.OK).entity(topCourseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/discounted")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDiscountedCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getDiscountedCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Not found any Discounted course at this movement for specified topic.")
          .type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @GET
  @Path("{search}/discounted/top")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDiscountedTopCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getDiscountedCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Not found any Discounted course at this movement for specified topic.")
          .type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> topCourseList;
    if (courseList.size() >= 10) {
      topCourseList = courseList.subList(0, 10);
    } else {
      topCourseList = courseList;
    }

    Response res = Response.status(Response.Status.OK).entity(topCourseList)
        .type(MediaType.APPLICATION_JSON).build();
    return res;
  }

  @SuppressWarnings("unchecked")
  @GET
  @Path("{search}/popular")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPopularCourseForTopic(@PathParam("search") String search) {
    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }
    List<Course> mostEnrolled = (List<Course>) getMostEnrolledTopCourses(search).getEntity();
    List<Course> mostReviewed = (List<Course>) getMostReviewedTopCourses(search).getEntity();

    List<Course> popularCourses = new ArrayList<Course>();
    popularCourses.addAll(mostEnrolled);

    List<Course> courseList = popularCourses.stream().filter(new Predicate<Course>() {

      @Override
      public boolean test(Course course) {
        return mostReviewed.contains(course);
      }
    }).sorted(new Comparator<Course>() {

      @Override
      public int compare(Course o1, Course o2) {
        return o2.getReviewCount() - o1.getReviewCount();
      }
    }).collect(Collectors.toList());

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Not found any popular course at this movement for specified topic.")
          .type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();

    return res;
  }

  @GET
  @Path("{search}/trend")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTrendingCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic to search").type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getTrendingCoursesForTopic(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Not found any trending course at this movement for specified topic.")
          .type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();

    return res;
  }

  @GET
  @Path("{search}/rating/{rate}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRatingFilterCourses(@PathParam("search") String search,
      @PathParam("rate") Double rating) {

    if (search.equals(null) || search.isEmpty() || rating == null || rating < 0.0
        || rating >= 5.0) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic/valid rating to search")
          .type(MediaType.APPLICATION_JSON)
          .build();
    }

    List<Course> courseList = service.getCoursesbasedOnRatingForTopic(search, rating);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Not found any course at this movement for specified topic.")
          .type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();

    return res;
  }

  @GET
  @Path("{search}/duration/{lenth}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRatingFilterCourses(@PathParam("search") String search,
      @PathParam("lenth") String lenth) {

    if (search.equals(null) || search.isEmpty() || lenth.equals(null) || lenth.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic/valid rating to search")
          .type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> courseList = service.getCoursesbasedOnLenthForTopic(search, lenth);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Not found any course at this movement for specified topic.")
          .type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();

    return res;
  }

  @GET
  @Path("{search}/newest")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getNewestCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic/valid rating to search")
          .type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> courseList = service.getNewestCourse(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Not found any course at this movement for specified topic.")
          .type(MediaType.APPLICATION_JSON).build();
    }

    Response res = Response.status(Response.Status.OK).entity(courseList)
        .type(MediaType.APPLICATION_JSON).build();

    return res;
  }

  @GET
  @Path("{search}/newest/top")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTopNewestCourses(@PathParam("search") String search) {

    if (search.equals(null) || search.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Rest URL doesn't contain topic/valid rating to search")
          .type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> courseList = service.getNewestCourse(search);

    if (courseList.size() == 0) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Not found any course at this movement for specified topic.")
          .type(MediaType.APPLICATION_JSON).build();
    }

    List<Course> topCourseList;
    if (courseList.size() >= 10) {
      topCourseList = courseList.subList(0, 10);
    } else {
      topCourseList = courseList;
    }

    Response res = Response.status(Response.Status.OK).entity(topCourseList)
        .type(MediaType.APPLICATION_JSON).build();

    return res;
  }

  // post api to append search topic in db
}
