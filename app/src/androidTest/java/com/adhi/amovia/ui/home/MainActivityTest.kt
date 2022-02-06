package com.adhi.amovia.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adhi.amovia.MainActivity
import com.adhi.amovia.R
import com.adhi.amovia.utils.DataDummy
import com.adhi.amovia.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    private val dummyMovies = DataDummy.dummyMovies()
    private val dummyTvShows = DataDummy.dummyTvShows()

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovies.size
            )
        )
    }

    @Test
    fun loadDetailMovie() {
        Thread.sleep(1000)
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating_count)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.chip_genre)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShow() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTvShows.size
            )
        )
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("TV Show")).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating_count)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.chip_genre)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteMovies() {
        Thread.sleep(2000)
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )
        onView(withId(R.id.btn_favorite)).perform(click())
        pressBack()
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.ib_delete)).perform(click())
        onView(withId(R.id.empty)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteTvShows() {
        onView(withText("TV SHOW")).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.rv_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )
        onView(withId(R.id.btn_favorite)).perform(click())
        pressBack()
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.ib_delete)).perform(click())
        onView(withId(R.id.empty)).check(matches(isDisplayed()))
    }
}