package com.ksy.adapter.service;

import com.ksy.adapter.exception.WeatherServiceException;
import com.ksy.adapter.model.IncomingMessageDTO;
import com.ksy.adapter.model.OutgoingMessageDTO;
import com.ksy.adapter.model.WeatherDataDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {
    private final WeatherService weatherService;
    private final SenderService senderService;

    /**
     * Method for processing messages
     * Process message from service A and send it to service B
     * @param incomingMessage message from service A
     * @throws WeatherServiceException if weather service is unavailable
     */
    public void processMessage(IncomingMessageDTO incomingMessage) throws WeatherServiceException {
        WeatherDataDTO weatherData = weatherService.getWeatherForCoordinates(
                incomingMessage.getCoordinates().getLatitude(),
                incomingMessage.getCoordinates().getLongitude()
        );

        OutgoingMessageDTO outgoingMessage = new OutgoingMessageDTO();
        outgoingMessage.setTxt(incomingMessage.getMsg());
        outgoingMessage.setCreatedDt(weatherData.getDate());
        outgoingMessage.setCurrentTemp(weatherData.getTemp());

        senderService.sendMessage(outgoingMessage);
    }
    public boolean checkLanguage(String lng){
        return lng.equals("ru");
    }

}
