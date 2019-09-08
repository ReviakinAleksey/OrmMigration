package com.ancode.ormmigration;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.ancode.ormmigration.ListMatchers.withAddress;
import static com.ancode.ormmigration.ListMatchers.withPersonName;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void afterActivityFinished() {
                    super.afterActivityFinished();
                    DaoApp app = (DaoApp) getActivity().getApplication();
                    DataBaseProvider.DbHelper dbHelper = new DataBaseProvider.DbHelper(app);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("DELETE FROM " + DataBaseProvider.DB_PERSONS_TABLE);
                    db.execSQL("DELETE FROM " + DataBaseProvider.DB_ADDRESSES_TABLE);
                }
            };

    @Test
    public void A_noDataAtStat() {
        onView(withId(R.id.persons_list)).check(
                matches(ListMatchers.withListSize(0)));

        onView(withId(R.id.addresses_list)).check(
                matches(ListMatchers.withListSize(0)));
    }

    @Test
    public void B_canAddPerson() {
        onView(withId(R.id.person_name)).perform(typeText("Person 1"));
        onView(withId(R.id.add_person_btn)).perform(click());
        onView(withId(R.id.person_name)).check(matches(withText("")));


        onData(withPersonName(is("Person 1")))
                .inAdapterView(withId(R.id.persons_list))
                .check(matches(isDisplayed()));

        onView(withId(R.id.addresses_list)).check(
                matches(ListMatchers.withListSize(0)));
    }

    @Test
    public void C_addAddress() {
        onView(withId(R.id.person_name)).perform(typeText("Person 1"));
        onView(withId(R.id.add_person_btn)).perform(click());

        onData(withPersonName(is("Person 1")))
                .inAdapterView(withId(R.id.persons_list))
                .perform(click());

        onView(withId(R.id.person_address)).perform(typeText("Address 1"));
        onView(withId(R.id.add_address_btn)).perform(click());
        onView(withId(R.id.person_address)).check(matches(withText("")));

        onData(withAddress(is("Address 1")))
                .inAdapterView(withId(R.id.addresses_list))
                .check(matches(isDisplayed()));

    }

    @Test
    public void D_switchPersons() {
        onView(withId(R.id.person_name)).perform(typeText("Person 1"));
        onView(withId(R.id.add_person_btn)).perform(click());
        onData(withPersonName(is("Person 1")))
                .inAdapterView(withId(R.id.persons_list))
                .perform(click());

        onView(withId(R.id.person_address)).perform(typeText("Address 1"));
        onView(withId(R.id.add_address_btn)).perform(click());

        onView(withId(R.id.person_name)).perform(typeText("Person 2"));
        onView(withId(R.id.add_person_btn)).perform(click());

        onData(withPersonName(is("Person 2")))
                .inAdapterView(withId(R.id.persons_list))
                .perform(click());

        onView(withId(R.id.person_address)).perform(typeText("Address 2"));
        onView(withId(R.id.add_address_btn)).perform(click());

        onData(withPersonName(is("Person 1")))
                .inAdapterView(withId(R.id.persons_list))
                .perform(click());

        onData(withAddress(is("Address 1")))
                .inAdapterView(withId(R.id.addresses_list))
                .check(matches(isDisplayed()));

        onView(withId(R.id.addresses_list)).check(
                matches(ListMatchers.withListSize(1)));

        onData(withPersonName(is("Person 2")))
                .inAdapterView(withId(R.id.persons_list))
                .perform(click());

        onData(withAddress(is("Address 2")))
                .inAdapterView(withId(R.id.addresses_list))
                .check(matches(isDisplayed()));

        onView(withId(R.id.addresses_list)).check(
                matches(ListMatchers.withListSize(1)));
    }
}