package com.ancode.ormmigration;

import android.view.View;
import android.widget.ListView;
import com.ancode.ormmigration.model.Address;
import com.ancode.ormmigration.model.Person;
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

    public static Matcher<Person> withPersonName(final Matcher nameMatcher) {
        return new TypeSafeMatcher<Person>() {
            @Override
            public boolean matchesSafely(Person person) {
                return nameMatcher.matches(person.name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("List should have a Persons with matches: " + nameMatcher.toString());
            }
        };
    }

    public static Matcher<Address> withAddress(final Matcher nameMatcher) {
        return new TypeSafeMatcher<Address>() {
            @Override
            public boolean matchesSafely(Address address) {
                return nameMatcher.matches(address.address);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("List should have a Address with matches: " + nameMatcher.toString());
            }
        };
    }
}