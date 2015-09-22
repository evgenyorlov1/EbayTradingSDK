package trading;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.call.ReviseItemCall;
import com.ebay.sdk.call.GetItemCall;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;

public class Trading {

    private static final String endPoint = "https://api.ebay.com/wsapi";    
    
    
    //here goes input from csv file
    public static void reviseItem(String token, String[] item) {        
        try {
            ApiContext apiContext = getApiCredentials(token);

            ReviseItemCall reviseCall = new ReviseItemCall(apiContext);
            GetItemCall getItemCall = new GetItemCall(apiContext);

            //retrieving item description and change old item name
            getItemCall.addDetailLevel(DetailLevelCodeType.ITEM_RETURN_DESCRIPTION);
            getItemCall.getItem(item[0]); // item by ebay number
            ItemType originalItem = getItemCall.getReturnedItem();
            String originalDescription = originalItem.getDescription();            
            String originalTitle = originalItem.getTitle();
                        
            int firstEntry = originalDescription.indexOf(">");         
            int lastEntry = originalDescription.indexOf("</", firstEntry);
            String firstHalf = originalDescription.substring(0, firstEntry+1);
            String secondHalf = originalDescription.substring(lastEntry);            
            String newDescription = firstHalf + item[1] + secondHalf;
                                    
            ItemType newItem = new ItemType();
            newItem.setItemID(item[0]);
            newItem.setTitle(item[1]);
            newItem.setDescription(newDescription);  //sets new description

            reviseCall.setItemToBeRevised(newItem);
            reviseCall.reviseItem();
        } catch (Exception e) {
            System.err.println("reviseItem error: " + e);
        }
    }

    protected static ApiContext getApiCredentials(String token) {
        ApiContext apiContext = new ApiContext();
        try {
            ApiCredential credential = apiContext.getApiCredential();
            credential.seteBayToken(token);
            apiContext.setApiServerUrl(endPoint);
        } catch (Exception e) {
            System.out.println("getApiCredentials error: " + e);
        }
        return apiContext;
    }
}
