package com.example.falljavafinal.controllers;

import com.example.falljavafinal.models.Listing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/listings")
public class ListingsController {

    //  this is the List of Listings
    //      add real estate properties are saved in this List
    private List<Listing> listings;

    //  this is our Controller constructor.
    //      here we will load the Listings.csv file
    //      which will be our database listing of homes in Dallas
    public ListingsController() {
        listings = Listing.loadRecords("src/Listings.csv");
    }

    //  This entry point will list all properties
    //
    //      If you want to see all the attributes of our Listing object they are listed below
    //          propertyType,address,city,zip,price,beds,baths,location,sqFt,lotSize,
    //          yearBuilt,daysOnMarket,pricePerSqFt,hoa,link,latitude,longitude
    /*
            There are 70 points to be earned in adding code to this controller
                There are 10 TODO's todo
                There is a possibility of getting an extra 5 points
    */
    @RequestMapping("/")
    public String list(Model model) {
        //      5 points
        //          TODO #1 add an attribute to be used to set the Title of the web page
        model.addAttribute("criteria", "All Properties");
        model.addAttribute("listings", listings);
        return "listListings";
    }

    //  entry point provided for you for the search request in the listing page
    //      initially in only receives the city name. You will add more to it in this exam
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    //      5 points
    //          TODO #2 Add in the extra search fields that are mentioned below
    //          in TODO's #3 - #10
    public String search(@RequestParam String city, Model model) {
        List<Listing> matchedHomes = listings;

        String criteria = "NOT YET IMPLEMENTED -- WRITE SOME CODE";

        if (city != null && city.length() > 0) {
        //      5 points
        //          TODO #3     call a method to find all listings that match our search city
            criteria += " for " + city;             //  this will build a string to show what we have searched for
        }

        //      5 points
        //          TODO #4     was the sqFt field entered?
        //                      If so call a method to find homes that are less than or equal to that size
        //                      EXTRA 5 points if you add to the criteria string so it contains the information about
        //                                     what we are searching for. Do this for each search field

        //      5 points
        //          TODO #5     was the price field entered?
        //                          If so call a method to find homes that are less than or equal to that price

        //      5 points
        //          TODO #6     was the beds field entered?
        //                          If so call a method to find homes that have at least that many bed rooms

        model.addAttribute("title", "Home Listings");
        model.addAttribute("criteria", criteria);
        model.addAttribute("listings", matchedHomes);
        return "listListings";
    }

    //  10 points
    //      TODO #7      Create a method that will find listings that match the city name entered in the search field
    public List<Listing> findByCity(List<Listing> list, String city) {
        //  you will add in your code to search for matching cities
        return null;            //  TODO !! DELETE THIS LINE OF CODE. It is only here so the app will compile
    }

    //  10 points
    //      TODO #8      Create a method that will find listings that are less than or equal to the square footage entered in the search field

    //  10 points
    //      TODO #9      Create a method that will find listings that are priced below what was entered in the search field

    //  10 points
    //      TODO #10      Create a method that will find listings that have at least the number of bed rooms entered in the search field
}
