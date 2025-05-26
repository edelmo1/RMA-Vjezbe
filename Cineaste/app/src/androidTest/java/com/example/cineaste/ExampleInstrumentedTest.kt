package com.example.cineaste

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.isBelow
import androidx.test.espresso.assertion.PositionAssertions.isRightOf
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntentInstrumentedTest {

    //za testiranje slike
    private fun withImage(@DrawableRes id: Int) = object : TypeSafeMatcher<View>(){

        override fun matchesSafely(item: View): Boolean {
            val context: Context = item.context
            val bitmap: Bitmap? = context.getDrawable(id)?.toBitmap()
            return item is ImageView && item.drawable.toBitmap().sameAs(bitmap)
        }

        override fun describeTo(description: org.hamcrest.Description?) {
            if (description != null) {
                description.appendText("Drawable does not contain image with id: $id")
            }
        }
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MovieDetailActivity> = ActivityScenarioRule(MovieDetailActivity::class.java)


    @Test
    fun testDetailActivityInstantiation(){
        val pokreniDetalje =
            Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Casino Royale")
        launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_title)).check(matches(withText("Casino Royale")))
        onView(withId(R.id.movie_genre)).check(matches(withText("action")))
        onView(withId(R.id.movie_overview)).check(matches(withSubstring("James Bond")))
        onView(withId(R.id.movie_poster)).check(matches(withImage(R.drawable.action)))

    }

    @Test
    fun testLinksIntent(){
        Intents.init()
        val pokreniDetalje =
            Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Casino Royale")
        launchActivity<MovieDetailActivity>(pokreniDetalje)
        onView(withId(R.id.movie_website)).perform(click())
        Intents.intended(hasAction(Intent.ACTION_VIEW))
        Intents.release()
    }

    @Test
    fun testRasporeda(){
        val pokreniDetalje =
            Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Casino Royale")
        launchActivity<MovieDetailActivity>(pokreniDetalje)

        onView(withId(R.id.movie_title)).check(isRightOf(withId(R.id.movie_genre)))
        onView(withId(R.id.movie_overview)).check(isBelow(withId(R.id.movie_website)))
    }

    @Test
    fun testQuery(){
        Intents.init()
        val pokreniDetalje =
            Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.putExtra("movie_title","Oppenheimer")
        launchActivity<MovieDetailActivity>(pokreniDetalje)

        val movieTitle = Uri.encode("Oppenheimer")
        val query = "$movieTitle trailer"
        val searchUri = Uri.parse("https://www.google.com/search?q=$query")
        onView(withId(R.id.movie_title)).perform(click())
        Intents.intended(hasData(searchUri))
        Intents.release()
    }
}

@RunWith(AndroidJUnit4::class)
class IntentInstrumentedTest2 {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)



    @Test
    fun testAction(){
        Intents.init()
        val pokreniDetalje =
            Intent(ApplicationProvider.getApplicationContext(),MovieDetailActivity::class.java)
        pokreniDetalje.setAction(Intent.ACTION_SEND)
        pokreniDetalje.putExtra("movie_title","Oppenheimer")
        launchActivity<MovieDetailActivity>(pokreniDetalje)

        onView(withId(R.id.searchText)).check(matches(withSubstring("Robert Oppenheimer")))
        Intents.release()
    }

    @Test
    fun testLista(){

        Intents.init()
        onView(withId(R.id.favMovies)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    hasDescendant(withText("Interstellar"))
                )
            )
        )

    Intents.init()
    onView(withId(R.id.favMovies)).perform(
        RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
            CoreMatchers.allOf(
                hasDescendant(withText("Casino Royale"))
            )
        )
    )

    Intents.init()
    onView(withId(R.id.favMovies)).perform(
        RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
            CoreMatchers.allOf(
                hasDescendant(withText("Cool Runnings"))
            )
        )
    )

    Intents.release()



    }
}
