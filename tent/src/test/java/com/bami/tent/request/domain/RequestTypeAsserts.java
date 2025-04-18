package com.bami.tent.request.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRequestTypeAllPropertiesEquals(RequestType expected, RequestType actual) {
        assertRequestTypeAutoGeneratedPropertiesEquals(expected, actual);
        assertRequestTypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRequestTypeAllUpdatablePropertiesEquals(RequestType expected, RequestType actual) {
        assertRequestTypeUpdatableFieldsEquals(expected, actual);
        assertRequestTypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRequestTypeAutoGeneratedPropertiesEquals(RequestType expected, RequestType actual) {
        assertThat(expected)
            .as("Verify RequestType auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRequestTypeUpdatableFieldsEquals(RequestType expected, RequestType actual) {
        assertThat(expected)
            .as("Verify RequestType relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getCreatedAt()).as("check createdAt").isEqualTo(actual.getCreatedAt()))
            .satisfies(e -> assertThat(e.getCreatedBy()).as("check createdBy").isEqualTo(actual.getCreatedBy()))
            .satisfies(e -> assertThat(e.getUpdatedAt()).as("check updatedAt").isEqualTo(actual.getUpdatedAt()))
            .satisfies(e -> assertThat(e.getUpdatedBy()).as("check updatedBy").isEqualTo(actual.getUpdatedBy()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRequestTypeUpdatableRelationshipsEquals(RequestType expected, RequestType actual) {}
}
