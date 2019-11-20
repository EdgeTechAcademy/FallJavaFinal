package com.example.falljavafinal.controllers;

import com.example.falljavafinal.models.Listing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/done")
public class FinishedController {

    //  this is the List of Listings
    //      add real estate properties are saved in this List
    private List<Listing> listings;

    //  this is our Controller constructor.
    //      here we will load the Listings.csv file
    //      which will be our database listing of homes in Dallas
    public FinishedController() {
        listings = Listing.loadRecords("src/Listings.csv");
    }

    //  This entry point will list all properties
    //
    //      If you want to see all the attributes of our Listing object they are listed below
    //          propertyType,address,city,zip,price,beds,baths,location,sqFt,lotSize,
    //          yearBuilt,daysOnMarket,pricePerSqFt,hoa,link,latitude,longitude
    @RequestMapping("/")
    public String list(Model model) {
        model.addAttribute("title", "Dallas Listings");
        model.addAttribute("criteria", "All Properties");
        model.addAttribute("listings", listings);
        return "done";
    }

    //  entry point provided for you for the search request in the listing page
    //      initially in only receives the city name. You will add more to it in this exam
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String city,  @RequestParam Integer price,
                         @RequestParam Integer sqFt, @RequestParam Integer beds, Model model) {
        List<Listing> matchedHomes = listings;

        String criteria = "Found Matching Listings:";

        if (city != null && city.length() > 0) {
            matchedHomes = findByCity(matchedHomes, city);
            criteria += " for City: " + city;
        }

        if (sqFt != null && sqFt > 0) {
            matchedHomes = findBySqFt(matchedHomes, sqFt);
            criteria += ", with at least: " + sqFt + " Sq Ft";
        }

        if (price != null && price > 0) {
            matchedHomes = findByPrice(matchedHomes, price);
            criteria += ", for less than $" + price;
        }

        if (beds != null && beds > 0) {
            matchedHomes = findByBeds(matchedHomes, beds);
            criteria += ", " + beds + "+ beds";
        }

        model.addAttribute("title", "Home Listings");
        model.addAttribute("criteria", criteria);
        model.addAttribute("listings", matchedHomes);
        return "done";
    }

    public List<Listing> findByCity(List<Listing> list, String city) {
        //  shortList will contain the listings whose city names was matched from the web page
        List<Listing> shortList = new ArrayList<>();

        city = city.toLowerCase();          //  lower case the name to standardize the format (all lowercase)
        //  look at all listings. One at a time
        for (Listing Listing : list) {
            //  check to see if the first or last name contains the name given
            //  we will convert the first and last names to lowercase since contains does a case sensitive compare
            if (Listing.getCity().toLowerCase().contains(city)) {
                shortList.add(Listing);
            }
        }
        //  return the list of Listings we found matching the name provided
        return shortList;
    }

    public List<Listing> findByPrice(List<Listing> list, int price) {
        //  shortList will contain the listings where the price is lower than the search price
        List<Listing> shortList = new ArrayList<>();

        //  look at all listings. One at a time
        for (Listing Listing : list) {
            //  check to see if the first or last name contains the name given
            //  we will convert the first and last names to lowercase since contains does a case sensitive compare
            if (Listing.getPrice() <= price) {
                shortList.add(Listing);
            }
        }
        //  return the list of Listings we found matching the name provided
        return shortList;
    }

    public List<Listing> findByBeds(List<Listing> list, int beds) {
        //  shortList will contain the listings with at least the number of bed rooms searched for
        List<Listing> shortList = new ArrayList<>();

        //  look at all listings. One at a time
        for (Listing Listing : list) {
            //  check to see if the first or last name contains the name given
            //  we will convert the first and last names to lowercase since contains does a case sensitive compare
            if (Listing.getBeds() >= beds) {
                shortList.add(Listing);
            }
        }
        //  return the list of Listings we found matching the name provided
        return shortList;
    }

    public List<Listing> findBySqFt(List<Listing> list, int sqFt) {
        //  shortList will contain the listings for all homes no bigger than the sq footage searched for
        List<Listing> shortList = new ArrayList<>();

        //  look at all listings. One at a time
        for (Listing Listing : list) {
            //  check to see if the first or last name contains the name given
            //  we will convert the first and last names to lowercase since contains does a case sensitive compare
            if (Listing.getSqFt() <= sqFt) {
                shortList.add(Listing);
            }
        }
        //  return the list of Listings we found matching the name provided
        return shortList;
    }
}
