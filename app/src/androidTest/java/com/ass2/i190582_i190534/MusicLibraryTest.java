package com.ass2.i190582_i190534;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MusicLibraryTest {

    @Rule
    public ActivityTestRule<MusicLibrary> a = new ActivityTestRule<>(MusicLibrary.class);
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testLikedSongs() {
        ActivityScenario.launch(MusicLibrary.class);
        Espresso.onView(withId(R.id.like)).perform(click());
        Espresso.onView(withId(R.id.music_lib)).check(matches(isDisplayed()));
    }

    @Test
    public void testListenLater() {
        ActivityScenario.launch(MusicLibrary.class);
        Espresso.onView(withId(R.id.listen_later)).perform(click());
        Espresso.onView(withId(R.id.music_lib)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearch() {
        ActivityScenario.launch(MusicLibrary.class);
        Espresso.onView(withId(R.id.search)).perform(click());
        Espresso.onView(withId(R.id.music_lib)).check(matches(isDisplayed()));
    }


    @After
    public void tearDown() throws Exception {
    }
}