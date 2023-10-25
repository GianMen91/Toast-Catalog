package com.toastcatalog

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.toastcatalog.webapi.NetworkClient
import com.toastcatalog.webapi.dto.Item
import org.junit.Assert
import org.junit.Test

class NetworkClientTests {

    private var receivedItems: List<Item>? = null
    private var expectedItems: List<Item>? = null

    private val expectedResponseData = """
        {
                "items":
                [
                    {
                        "name": "Avocado Toast",
                        "price": "5.99",
                        "id": 1,
                        "currency": "EUR",
                        "last_sold": "2020-11-28T15:14:22Z"
                    },
                    {
                        "name": "Bacon Toast",
                        "id": 2,
                        "price": "1.99",
                        "currency": "EUR",
                        "last_sold": "2021-01-30T02:24:04Z"
                    },
                    {
                        "name": "Crunchy Toast",
                        "id": 3,
                        "price": "0.99",
                        "currency": "EUR",
                        "last_sold": "2021-03-17T03:45:47Z"
                    },
                    {
                        "name": "Egg in the basket",
                        "id": 4,
                        "price": "2.99",
                        "currency": "EUR",
                        "last_sold": "2020-07-12T02:43:38Z"
                    },
                    {
                        "name": "French Toast",
                        "id": 5,
                        "price": "3.99",
                        "currency": "EUR",
                        "last_sold": "2021-04-11T14:45:28Z"
                    },        {
                        "name": "Milk Toast",
                        "id": 6,
                        "price": "1.99",
                        "currency": "EUR",
                        "last_sold": "2020-08-05T13:39:11Z"
                    },
                    {
                        "name": "Ogura Toast",
                        "id": 7,
                        "price": "4.99",
                        "currency": "EUR",
                        "last_sold": "2020-09-11T23:59:58Z"
                    },
                    {
                        "name": "Prawn Sesame Toast",
                        "id": 8,
                        "price": "5.99",
                        "currency": "EUR",
                        "last_sold": "2021-06-20T23:13:28Z"
                    },
                    {
                        "name": "Toast Hawaii",
                        "id": 9,
                        "price": "3.99",
                        "currency": "EUR",
                        "last_sold": "2020-12-28T21:37:20Z"
                    },
                    {
                        "name": "Hummus Toast",
                        "id": 10,
                        "price": "1.99",
                        "currency": "EUR",
                        "last_sold": "2022-03-23T12:38:26Z"
                    },
                    {
                        "name": "Whipped Ricotta Toast",
                        "id": 11,
                        "price": "2.99",
                        "currency": "EUR",
                        "last_sold": "2022-07-30T09:13:34Z"
                    },
                    {
                        "name": "Sweet Potato Toast",
                        "id": 12,
                        "price": "3.99",
                        "currency": "EUR",
                        "last_sold": "2022-01-15T07:03:09Z"
                    },
                    {
                        "name": "Tuna Toast",
                        "id": 13,
                        "price": "2.99",
                        "currency": "EUR",
                        "last_sold": "2022-03-16T10:55:17Z"
                    }
                ]
        }
    """

    @Test
    fun testGetItemsSuccess() {

        val networkClient = NetworkClient { items ->
            if (items != null) {
                // Update the RecyclerView adapter with the retrieved items
                receivedItems = items;
            } else {
                // Handle API error or network failure
                // You can show an error message to the user here if needed.
            }
        }

        // Call the API to fetch items
        networkClient.getItems()

        // Wait for the network request to complete
        Thread.sleep(3000) // Adjust as needed

        val gson = GsonBuilder().create()
        val responseContainerType = object : TypeToken<ResponseContainer>() {}.type
        val responseContainer =
            gson.fromJson<ResponseContainer>(expectedResponseData, responseContainerType)
        expectedItems = responseContainer.items

        // Test receivedItems and expectedItems have the same size
        Assert.assertEquals(expectedItems!!.size, receivedItems!!.size)

        // Test receivedItems is not null
        Assert.assertNotNull(receivedItems)

        // Test receivedItems and expectedItems have the same elements
        Assert.assertTrue(compareItemLists(expectedItems, receivedItems))
    }

    @Test
    fun testGetItemsFailure() {

        receivedItems = null;

        val gson = GsonBuilder().create()
        val responseContainerType = object : TypeToken<ResponseContainer>() {}.type
        val responseContainer =
            gson.fromJson<ResponseContainer>(expectedResponseData, responseContainerType)
        expectedItems = responseContainer.items

        // Test receivedItems and expectedItems do not have the same size
        Assert.assertNotEquals(expectedItems?.size, receivedItems?.size)

        // Test receivedItems is null
        Assert.assertNull(receivedItems)

        // Test receivedItems and expectedItems do not have the same elements
        Assert.assertFalse(compareItemLists(expectedItems, receivedItems))
    }

    private fun compareItemLists(expectedItems: List<Item>?, receivedItems: List<Item>?): Boolean {
        if(expectedItems==null||receivedItems==null){
            return false
        }

        for (i in expectedItems.indices) {
            if ((receivedItems[i].id != expectedItems[i].id) ||
                (receivedItems[i].name != expectedItems[i].name) ||
                (receivedItems[i].currency != expectedItems[i].currency) ||
                (receivedItems[i].price != expectedItems[i].price) ||
                (receivedItems[i].last_sold != expectedItems[i].last_sold)
            ) {
                return false
            }
        }
        
        return true
    }

    data class ResponseContainer(val items: List<Item>)
}
