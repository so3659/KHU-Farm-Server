package com.laicos.khufarm.domain.delivery.service;

public interface DeliveryTrackerApiClient {
    void registerTrackWebhook(String carrierId, String trackingNumber, String callbackUrl, String expirationTime);
}
