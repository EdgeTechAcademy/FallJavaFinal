package com.example.falljavafinal.controllers;

import com.example.falljavafinal.models.Listing;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

//      RestController to process our API requests
//      the root URL will be /api
@RestController
@RequestMapping("/api")
public class APIController {

    //  this will be a reference over to the original List of Listings in the ListingsController
    private List<Listing> listings;

    //  we could read the listings.csv file on our own but that would be a waste
    //  we will use Dependency Injection to get the ListingsController which has already loaded the
    //  array of listings. We will save the listings for our own personal use
    public APIController (ListingsController listingsController) {
        listings = listingsController.getListings();
    }

    /**
     *      justAFew
     *          the requesting API call will have a Path Variable to specify the number of desired property listings
     *          localhost::8080/api/count/42
     * @param number        number of properties to return
     * @return              List of Listings
     */
    @RequestMapping("count/{number}")
    public List<Listing> justAFew(@PathVariable Integer number) {

        //  stream the listings array and grab the first 'number' of homes for sale
        //  collect the result into a new List
        List<Listing> smallList = listings.stream().limit(number).collect(Collectors.toList());

        //  return this final list to the user. It will be converted to JSON
        return smallList;
    }
}
