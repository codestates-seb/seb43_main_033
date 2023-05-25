import React, { useState,useEffect } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import { Event as CalendarEvent } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import WorkRecordTable from "../MyWorkPage/WorkRecordTable";
import { v4 as uuidv4 } from 'uuid';


const localizer = momentLocalizer(moment);

interface Event {
  id: string; 
  start: Date;
  end: Date;
  title: string;
}


const MyCalendar = () => {
  const [dialogIsOpen, setDialogIsOpen] = useState<boolean>(false);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());
  const [events, setEvents] = useState<Event[]>([]);





  const handleSelectEvent = (event: CalendarEvent) => {
    setSelectedDate(new Date(moment(event.start).format("YYYY-MM-DD")));
    setDialogIsOpen(true);
  };

  const handleSelectSlot = (slotInfo: { start: Date; end: Date }) => {
    setSelectedDate(new Date(moment(slotInfo.start).format("YYYY-MM-DD")));
    setDialogIsOpen(true);
  };

  const updateEvents = (updatedEvents: Event[]) => {
    setEvents(updatedEvents);
    
  };
  
  const updateEvent = (updatedEvent: CalendarEvent) => {
    const updatedEvents = events.map((event) => {
      if (event.start === updatedEvent.start && event.end === updatedEvent.end) {
        return {
          ...event,
          title: String(updatedEvent.title),
        };
      }
      return event;
    });
    updateEvents(updatedEvents);
  };

  const editEvent = (eventId: string, updatedEvent: CalendarEvent) => {
    const editedEvent: Event = {
      ...updatedEvent,
      id: eventId, 
      start: moment(updatedEvent.start).subtract(1, 'hours').toDate(),
      end: moment(updatedEvent.end).add(1, 'hours').toDate(),
      title: `${updatedEvent.title} (수정됨)`,
    };
  
    const updatedEvents = events.map((event) => {
      if (event.id === eventId) { 
        return editedEvent;
      }
      return event;
    });
  
    updateEvents(updatedEvents);
    updateEvent(editedEvent);
  };
  

  const addEvent = (
    date: Date,
    startWork: string,
    endWork: string,
    note: string
  ) => {
    const startTime = moment(
      `${moment(date).format("YYYY-MM-DD")}T${startWork}:00.000`
    );
    const endTime = moment(
      `${moment(date).format("YYYY-MM-DD")}T${endWork}:00.000`
    );

    const formattedNote = `출근: ${startWork}, 퇴근: ${endWork}\n${note}`;

    const newEvent: Event = {
      id: uuidv4(), 
      start: startTime.toDate(),
      end: endTime.toDate(),
      title: formattedNote,
    };
    
    const updatedEvents = [...events, newEvent];
    updateEvents(updatedEvents);
  };

  const deleteEvent = (event: CalendarEvent) => {
    const updatedEvents = events.filter((e) => e !== event);
    updateEvents(updatedEvents);
    
  };
  

  const eventStyleGetter = () => {
    const backgroundColor = "#34d399";
    const style = {
      backgroundColor,
      borderRadius: "0px",
      opacity: 0.8,
      color: "white",
      border: "0px",
    };
    return {
      style
    };
  };

  const closeDialog = () => {
    setDialogIsOpen(false);
  };

  return (
    <div className="relative">
      <Calendar
        localizer={localizer}
        events={events}
        startAccessor="start"
        endAccessor="end"
        style={{ height: 600, width: 1000 }}
        eventPropGetter={eventStyleGetter}
        selectable={true}
        onSelectEvent={handleSelectEvent}
        onSelectSlot={handleSelectSlot}
      />
      {dialogIsOpen && (
        <div className="z-10 fixed top-0 left-0 flex items-center justify-center w-screen h-screen bg-black bg-opacity-50">
          <div className="bg-white rounded-lg">
            <div className="flex justify-between p-3">
              <h3 className="font-bold">근무 현황 기록표</h3>
              <button className="font-bold" onClick={closeDialog}>
                X
              </button>
            </div>
            <div className="p-3">
              <WorkRecordTable
                date={selectedDate}
                addEvent={addEvent}
                deleteEvent={deleteEvent}
                editEvent={editEvent} 
              />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
export default MyCalendar;