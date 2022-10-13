import React, { useEffect, useState } from "react";
import TUICalendar from '@toast-ui/react-calendar';
import '@toast-ui/calendar/dist/toastui-calendar.min.css';
  
const API_GATEWAY_ENDPOINT = 'https://sirbrnssa4.execute-api.us-west-2.amazonaws.com/Prod/primeGamingOffers';

const CalendarComponent = () => {
    const [offers, setOffers] = useState([]);
    useEffect(() => {
        const fetchData = async () => {
            const result = await fetch(API_GATEWAY_ENDPOINT, {
                method: 'POST',
                headers: {},
                body: `{"startDate":"2022-10-01T00:00:00Z","endDate":"","gameTitle":"","publisher":"Riot Games, Inc."}`,
              });
            const responseJson = await result.json()
            console.log(responseJson);
            const primeGamingOfferList = responseJson.primeGamingOfferList;
            console.log(primeGamingOfferList);
            setOffers(primeGamingOfferList);
        };
        fetchData();
     }, []);

     function addHours(numOfHours, date = new Date()) {
        date.setTime(date.getTime() + numOfHours * 60 * 60 * 1000);
      
        return date;
      }

     const getCalendarEvents = () => {
        return offers.map(offer =>(
            {
                id: offer.title,
                title: offer.title,
                category: 'time',
                start: offer.startTime,
                end: addHours(2, new Date(offer.startTime)).toUTCString(),
                body: offer.description
            }
        ))
    }
    
    return (
        <div>
            <TUICalendar
                height="500px"
                width="1000px"
                view="month"
                events={getCalendarEvents()}
                useDetailPopup="true"
            />
         </div>
    );
}

export default CalendarComponent;