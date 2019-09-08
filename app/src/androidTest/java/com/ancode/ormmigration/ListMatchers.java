package com.ancode.ormmigration;

import android.view.View;
import android.widget.ListView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ListMatchers {

    public static Matcher<View> withListSize(final int size) {
        return new TypeSafeMatcher<View>() {

            private int actualCount;

            @Override
            public boolean matchesSafely(final View view) {
                actualCount = ((ListView) view).getCount();
                return actualCount == size;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("ListView should have " + size + " items, got: " + actualCount);
            }
        };
    }

    public static Matcher<DataBaseProvider.Person> withPersonName(final Matcher nameMatcher) {
        return new TypeSafeMatcher<DataBaseProvider.Person>() {
            @Override
            public boolean matchesSafely(DataBaseProvider.Person person) {
                return nameMatcher.matches(person.name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("List should have a Persons with matches: " + nameMatcher.toString());
            }
        };
    }

    public static Matcher<DataBaseProvider.Address> withAddress(final Matcher nameMatcher) {
        return new TypeSafeMatcher<DataBaseProvider.Address>() {
            @Override
            public boolean matchesSafely(DataBaseProvider.Address address) {
                return nameMatcher.matches(address.address);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("List should have a Address with matches: " + nameMatcher.toString());
            }
        };
    }
}