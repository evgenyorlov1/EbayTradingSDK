package trading;


import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.call.GeteBayOfficialTimeCall;
import com.ebay.sdk.call.ReviseItemCall;
import com.ebay.sdk.call.GetItemCall;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.ProductListingDetailsType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;


public class Trading {

    private static final String endPoint = "https://api.ebay.com/wsapi";
    private static final String authToken = "AgAAAA**AQAAAA**aAAAAA**t7nVVQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AHmISiC5CDogydj6x9nY+seQ**qfYCAA**AAMAAA**5J9YEaAzRdFJxVqIoFpAmA6bAFBNgU2TvFK9Wxt4WBl6fhHIxERPR57IzbmzSeCEsVoFrwVb+T/ZYU3eObXT4hbzwjbNwC2JAiIvGggnWAp6drSIu+gqjFuOhDMiGk5c/4KPkzO9rB6rmwSe5vrBoqq4LZ6nzAWYQfDjqd+xrTUUWGLWtgaGcK0LzLiMuvEcOQfKFDb0OOqItA+g+xwcaq2zS1yTrnJkkuqV8CQpCOm6+5Tb6PLfhyN49R1Tn4eLNBY8qgkANws339QA3HJxqqQtzM/G2NwzVy0QdzyJ6C+B0EFE1NVypRXcF0nPUxCkM4U0FOKZIIxLXB6fD5zjIIoGwprGH9VYQkI+js3OFLVLjy3EnC3dIG1TOhEOSQH5WLyXKm68iTEguKftOqCMyFy5uFXckjMjjiZxyBQ0eljiXmWkoo2bqJuHNFd3Q1PGT8lF6d5dNX2Ok/SicjZs7brkCZG+ARSOI8n7i2YDFYDn8PHCBj3OAyyx3XsyBmWu+SCLzME9XsjaK/XlsQtENTMoSTnhWpkwha+K2bsnzmqHQDCZ05zBiadtx7lGmqMcg6jzzcY23hCfa56gS8g1lTQ929RC5XSO5rhQFKsnPkICopnUB0+S0S/YSrwlxb3VT+yG0LK724OPjuCk2HGeyv7vnftL6kAVz2znHCf8g0kFve19C0PMHbd5odSzDK8nXT2Up2I1bBQ5NsA8rHNQo2IQzzOnrotWiqmwcvyxmPPe3KzpaKY+xMdACS9IDaDh";        
    
    
    public static void main(String args[]) {
        String[] item = {"121759200620", "New title"};
        reviseItem(item);
    }
    
    //here goes input from csv file
    public static void reviseItem(String[] item) {
        try {
            ApiContext apiContext = getApiCredentials();
            
            ReviseItemCall reviseCall = new ReviseItemCall(apiContext);
            GetItemCall getItemCall = new GetItemCall(apiContext);
            
            
            //retrieving item description and change old item name
            getItemCall.addDetailLevel(DetailLevelCodeType.ITEM_RETURN_DESCRIPTION);
            getItemCall.getItem(item[0]); // item by ebay number
            ItemType originalItem = getItemCall.getReturnedItem();                                                
            String originalDescription = originalItem.getDescription();
            String originalTitle = originalItem.getTitle();
            String newDescription = originalDescription.replaceFirst(originalTitle, item[1]);  // replacing original description with new item name
            
            
            ItemType newItem = new ItemType();
            newItem.setItemID(item[0]);  
            newItem.setTitle(item[1]);  
            newItem.setDescription(newDescription);  //sets new description            
            
            reviseCall.setItemToBeRevised(newItem);
            reviseCall.reviseItem();                                                   
            //ProductListingDetailsType details = new ProductListingDetailsType();
            //details.setEAN("5033775206402"); //get EAN from .csv file
            //item.setProductListingDetails(details);                                         
        } catch (Exception e) {System.err.println("reviseItem error: " + e);}
    }

    protected static ApiContext getApiCredentials() {
        ApiContext apiContext = new ApiContext();
        try {            
            ApiCredential credential = apiContext.getApiCredential();
            credential.seteBayToken(authToken);
            //uncomment two below and for release
            //credential.setApiAccount("");
            //credential.seteBayAccount("");
            apiContext.setApiServerUrl(endPoint);            
        } catch(Exception e) {System.out.println("getApiCredentials error: " + e);}
        return apiContext;
    }
}
