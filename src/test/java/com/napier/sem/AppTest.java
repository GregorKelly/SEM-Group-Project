package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    @Test
    void CountriesWorldNotNull()
    {
        app.GetCountriesCont(null);
    }

    @Test
    void CountriesContInvalid()
    {
        app.GetCountriesCont("TestCont");
}
}