package ru.scadouge.ewm.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EwmSqlHelper {
    public static final String TABLE_EWM_USERS = "ewm_users";
    public static final String TABLE_EWM_EVENTS = "ewm_events";
    public static final String TABLE_EWM_LOCATIONS = "ewm_locations";
    public static final String TABLE_EWM_REQUESTS = "ewm_requests";
    public static final String TABLE_EWM_CATEGORIES = "ewm_categories";
    public static final String TABLE_EWM_COMPILATIONS = "ewm_compilations";
    public static final String TABLE_EWM_COMPILATION_EVENT = "ewm_compilation_event";

    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";

    public static final String CATEGORY_ID = "id";
    public static final String CATEGORY_NAME = "name";

    public static final String EVENT_ID = "id";
    public static final String EVENT_TITLE = "title";
    public static final String EVENT_DESCRIPTION = "description";
    public static final String EVENT_ANNOTATION = "annotation";
    public static final String EVENT_CATEGORY_ID = "category_id";
    public static final String EVENT_INITIATOR_ID = "initiator_id";
    public static final String EVENT_DATE = "event_date";
    public static final String EVENT_LOCATION = "location_id";
    public static final String EVENT_REQUEST_MODERATION = "request_moderation";
    public static final String EVENT_PAID = "paid";
    public static final String EVENT_PARTICIPANT_LIMIT = "participant_limit";
    public static final String EVENT_STATE = "state";
    public static final String EVENT_PUBLISHED_ON = "published_on";
    public static final String EVENT_CREATED_ON = "created_on";
    public static final String EVENT_CONFIRMED_REQUESTS = "confirmed_requests";

    public static final String REQUEST_ID = "id";
    public static final String REQUEST_USER_ID = "user_id";
    public static final String REQUEST_EVENT_ID = "event_id";
    public static final String REQUEST_STATUS = "status";
    public static final String REQUEST_CREATED = "created";

    public static final String LOCATION_ID = "id";
    public static final String LOCATION_NAME = "name";
    public static final String LOCATION_LAT = "lat";
    public static final String LOCATION_LON = "lon";

    public static final String COMPILATION_ID = "id";
    public static final String COMPILATION_TITLE = "title";
    public static final String COMPILATION_PINNED = "pinned";

    public static final String COMPILATION_COMPILATION_ID = "compilation_id";
    public static final String COMPILATION_EVENT_ID = "event_id";
}
