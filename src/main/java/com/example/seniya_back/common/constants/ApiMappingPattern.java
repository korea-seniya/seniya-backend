package com.example.seniya_back.common.constants;

public interface ApiMappingPattern {
    String AUTH_API = "/api/v1/auth";
    String USER_API = "/api/v1/users";
    String PAYMENT_API = "/api/v1/payments";
    String TRAINER_APPLY_API = "/api/v1/trainer-applications";
    String INQUIRY_API = "/api/v1/inquiries";
    String POST_API = "/api/v1/posts";
    String HEALTH_DATA_API = "/api/v1/health-data";

    String NOTICE_API = "/api/v1/notices";
    String ADMIN_API = "/api/v1/admin";
    String PARTICIPATION_API = "/api/v1/participations";
    String USER_COURSE_API = "/api/v1/courses";
    String ADMIN_COURSE_API = "/api/v1/admin/courses";
    String ADMIN_USER_API = "/api/v1/admin/users";

    String TRAINER_PROFILE_API = "/api/v1/trainer-profiles";
    String EMAIL_API = "/email";
    String EMAIL_VERIFY_API = AUTH_API + "/emailVerify";
    String COMMENT_API = POST_API + "/{postId}/comments";

    String COURSE_FILTER_API = "/api/v1/courses/filter";

}