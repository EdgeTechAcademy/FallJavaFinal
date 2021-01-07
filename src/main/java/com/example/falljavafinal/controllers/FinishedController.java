package com.example.falljavafinal.controllers;

import com.example.falljavafinal.models.Listing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/done")
public class FinishedController {

    //  this is the List of Listings
    //      add real estate properties are saved in this List
    private List<Listing> listings;
    Listing averageListing = new Listing("", "", "Averages for these Listings:", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    //  this is our Controller constructor.
    //      here we will load the Listings.csv file
    //      which will be our database listing of homes in Dallas
    public FinishedController() {
        listings = Listing.loadRecords("src/Listings.csv");
        listings.add(0, averageListing);
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
    public String search(@RequestParam String city, @RequestParam Integer price,
                         @RequestParam Integer sqFt, @RequestParam Integer beds,
                         @RequestParam String orderBy, @RequestParam Integer ascDesc , Model model) {
        List<Listing> matchedHomes = listings;

        String criteria = "Found Matching Listings for Homes ";

        if (city != null && city.length() > 0) {
            matchedHomes = matchedHomes.stream().filter(l -> l.getCity().equalsIgnoreCase(city)).collect(Collectors.toList());
            criteria += "in the City of " + city + " ";
        }

        if (sqFt != null && sqFt > 0) {
            matchedHomes = matchedHomes.stream().filter(l -> l.getSqFt() == sqFt).collect(Collectors.toList());
            criteria += "with at least: " + sqFt + " Sq Ft ";
        }

        if (price != null && price > 0) {
            matchedHomes = matchedHomes.stream().filter(l -> l.getPrice() == price).collect(Collectors.toList());
            criteria += "for less than $" + String.format("%,d", price) + " ";
        }

        if (beds != null && beds > 0) {
            matchedHomes = matchedHomes.stream().filter(l -> l.getBeds() == beds).collect(Collectors.toList());
            criteria += "with " + beds + "+ bed rooms ";
        }

        switch (orderBy) {
            case "city":  Collections.sort(matchedHomes, (l1, l2) -> ascDesc * (l1.getCity() .compareTo(l2.getCity())));    break;
            case "price": Collections.sort(matchedHomes, (l1, l2) -> ascDesc * (l1.getPrice() - l2.getPrice()));            break;
            case "sqFt":  Collections.sort(matchedHomes, (l1, l2) -> ascDesc * (l1.getSqFt()  -  l2.getSqFt()));            break;
            case "beds":  Collections.sort(matchedHomes, (l1, l2) -> ascDesc * (l1.getBeds()  -  l2.getBeds()));            break;
        }

        if (matchedHomes.size() > 0) {
            int avePrice = 0, aveSqFt = 0;
            for (Listing listing : matchedHomes) {
                avePrice += listing.getPrice();
                aveSqFt += listing.getSqFt();
            }
            averageListing.setPrice(avePrice / matchedHomes.size());
            averageListing.setSqFt(aveSqFt / matchedHomes.size());
            matchedHomes.remove(averageListing);
            matchedHomes.add(0,averageListing);
        }

        model.addAttribute("beds", beds);
        model.addAttribute("price", price);
        model.addAttribute("city", city);
        model.addAttribute("sqFt", sqFt);
        model.addAttribute("title", "Home Listings");
        model.addAttribute("criteria", criteria);
        model.addAttribute("listings", matchedHomes);
        return "done";
    }
}
