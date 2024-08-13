package ru.scadouge.ewm.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("checkstyle:MemberName")
public class EwmSqlHelper {
    public final String TABLE_EWM_COMPILATIONS = "ewm_compilations";
    public final String TABLE_EWM_USERS = "ewm_users";
    public final String TABLE_EWM_EVENTS = "ewm_events";
    public final String TABLE_EWM_LOCATIONS = "ewm_locations";
    public final String TABLE_EWM_REQUESTS = "ewm_requests";
    public final String TABLE_EWM_CATEGORIES = "ewm_categories";
    public final String TABLE_EWM_COMPILATION_EVENT = "ewm_compilation_event";

    public final String USER_ID = "id";
    public final String USER_NAME = "name";
    public final String USER_EMAIL = "email";

    public final String CATEGORY_ID = "id";
    public final String CATEGORY_NAME = "name";

    public final String EVENT_ID = "id";
    public final String EVENT_TITLE = "title";
    public final String EVENT_DESCRIPTION = "description";
    public final String EVENT_ANNOTATION = "annotation";
    public final String EVENT_CATEGORY_ID = "category_id";
    public final String EVENT_INITIATOR_ID = "initiator_id";
    public final String EVENT_DATE = "event_date";
    public final String EVENT_LOCATION = "location_id";
    public final String EVENT_REQUEST_MODERATION = "request_moderation";
    public final String EVENT_PAID = "paid";
    public final String EVENT_PARTICIPANT_LIMIT = "participant_limit";
    public final String EVENT_STATE = "state";
    public final String EVENT_PUBLISHED_ON = "published_on";
    public final String EVENT_CREATED_ON = "created_on";
    public final String EVENT_CONFIRMED_REQUESTS = "confirmed_requests";

    public final String REQUEST_ID = "id";
    public final String REQUEST_USER_ID = "user_id";
    public final String REQUEST_EVENT_ID = "event_id";
    public final String REQUEST_STATUS = "status";
    public final String REQUEST_CREATED = "created";

    public final String LOCATION_ID = "id";
    public final String LOCATION_NAME = "name";
    public final String LOCATION_EXPIRATION_DATE = "expiration_date";
    public final String LOCATION_PERMANENT = "permanent";
    public final String LOCATION_LAT = "lat";
    public final String LOCATION_LON = "lon";
    public final String LOCATION_RADIUS = "radius";

    public final String COMPILATION_ID = "id";
    public final String COMPILATION_TITLE = "title";
    public final String COMPILATION_PINNED = "pinned";

    public final String COMPILATION_COMPILATION_ID = "compilation_id";
    public final String COMPILATION_EVENT_ID = "event_id";
}
