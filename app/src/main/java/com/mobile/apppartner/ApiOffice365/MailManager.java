package com.mobile.apppartner.ApiOffice365;

import android.util.Log;

import com.microsoft.services.orc.resolvers.ADALDependencyResolver;
import com.microsoft.services.outlook.BodyType;
import com.microsoft.services.outlook.EmailAddress;
import com.microsoft.services.outlook.ItemBody;
import com.microsoft.services.outlook.Message;
import com.microsoft.services.outlook.Recipient;
import com.microsoft.services.outlook.fetchers.OutlookClient;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.concurrent.ExecutionException;

public class MailManager {

    private static final String TAG = "MailManager";

    private String mServiceResourceId;
    private String mServiceEndpointUri;

    /**
     * Sends an email message using the Office 365 mail capability from the address of the
     * signed in user. You need to initialize the MailManager by calling
     * - {@link MailManager#setServiceResourceId(String)}
     * - {@link MailManager#setServiceEndpointUri(String)}
     * @param emailAddress The recipient email address.
     * @param subject The subject to use in the mail message.
     * @param body The body of the message.
     * @param operationCallback The callback to which return the result or error.
     */
    public void sendMail(final String emailAddress, final String subject, final String body, final OperationCallback<Integer> operationCallback) {

        if(!isReady()){
            throw new MissingResourceException(
                    "You must set the ServiceResourceId and ServiceEndPointUri before using sendMail",
                    "MailManager",
                    "ServiceResourceId, ServiceEndPointUri"
            );
        }

        // Since we're doing considerable work, let's get out of the main thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AuthenticationManager.getInstance().setResourceId(mServiceResourceId);
                    ADALDependencyResolver dependencyResolver = (ADALDependencyResolver) AuthenticationManager
                            .getInstance()
                            .getDependencyResolver();

                    OutlookClient mailClient = new OutlookClient(mServiceEndpointUri, dependencyResolver);

                    // Prepare the message.
                    List<Recipient> recipientList = new ArrayList<>();

                    Recipient recipient = new Recipient();
                    EmailAddress email = new EmailAddress();
                    email.setAddress(emailAddress);
                    recipient.setEmailAddress(email);
                    recipientList.add(recipient);

                    Message messageToSend = new Message();
                    messageToSend.setToRecipients(recipientList);

                    ItemBody bodyItem = new ItemBody();
                    bodyItem.setContentType(BodyType.HTML);
                    bodyItem.setContent(body);
                    messageToSend.setBody(bodyItem);
                    messageToSend.setSubject(subject);

                    // Contact the Office 365 service and deliver the message.
                    Integer mailId = mailClient
                            .getMe()
                            .getOperations()
                            .sendMail(messageToSend, true).get();

                    Log.i(TAG, "sendMail - Email with ID: " + mailId + "sent");
                    operationCallback.onSuccess(mailId);
                } catch (InterruptedException | ExecutionException e) {
                    Log.e(TAG, "sendMail - " + e.getMessage());
                    operationCallback.onError(e);
                }
            }
        }).start();
    }

    public static synchronized MailManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MailManager();
        }
        return INSTANCE;
    }

    private static MailManager INSTANCE;

    /**
     * Store the service resource id from the discovered service.
     * @param serviceResourceId The service resource id obtained from the discovery service.
     */
    public void setServiceResourceId(final String serviceResourceId) {
        this.mServiceResourceId = serviceResourceId;
    }

    /**
     * Store the service endpoint uri from the discovered service.
     * @param serviceEndpointUri The service endpoint uri obtained from the discovery service.
     */
    public void setServiceEndpointUri(final String serviceEndpointUri) {
        this.mServiceEndpointUri = serviceEndpointUri;
    }

    /**
     * Check to see if the service resource id and service endpoint uri values have been set.
     * @return True if service resource id and service endpoint uri have been set, false otherwise.
     */
    private boolean isReady(){
        return mServiceEndpointUri != null && mServiceResourceId != null;
    }
}
