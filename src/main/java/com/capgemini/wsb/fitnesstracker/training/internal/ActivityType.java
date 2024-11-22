package com.capgemini.wsb.fitnesstracker.training.internal;

/**
 * Enum representing different types of physical activities.
 * Each activity type has a display name associated with it.
 */
public enum ActivityType {

    /**
     * Represents running activity.
     */
    RUNNING("Running"),

    /**
     * Represents cycling activity.
     */
    CYCLING("Cycling"),

    /**
     * Represents walking activity.
     */
    WALKING("Walking"),

    /**
     * Represents swimming activity.
     */
    SWIMMING("Swimming"),

    /**
     * Represents tennis activity.
     */
    TENNIS("Tennis");

    private final String displayName;

    /**
     * Constructs an ActivityType with the given display name.
     *
     * @param displayName the human-readable name of the activity
     */
    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the activity.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

}
