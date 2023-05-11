"use client";
import React, { useState } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import { Event as CalendarEvent } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
// import { Dialog, DialogTitle, DialogContent } from "@material-ui/core";
import WorkRecordTable from "./WorkRecordTable";

const localizer = momentLocalizer(moment);

interface Event {
  start: Date;

  end: Date;
  title: string;
}

const MyCalendar = () => {
  const [dialogIsOpen, setDialogIsOpen] = useState<boolean>(false);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());
  const [events, setEvents] = useState<Event[]>([]);

  const updateEvents = (updatedEvents: Event[]) => {
    setEvents(updatedEvents);
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

  const handleSelectEvent = (event: CalendarEvent) => {
    setSelectedDate(new Date(moment(event.start).format("YYYY-MM-DD")));
    setDialogIsOpen(true);
  };

  const handleSelectSlot = (slotInfo: { start: Date; end: Date }) => {
    setSelectedDate(new Date(moment(slotInfo.start).format("YYYY-MM-DD")));
    setDialogIsOpen(true);
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
      style: style,
    };
  };

  return (
    <div>
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
      {/* <Dialog open={dialogIsOpen} onClose={() => setDialogIsOpen(false)}>
        <DialogTitle className="font-bold">
          근무 현황 기록표
          <button
            className="float-right font-bold"
            onClick={() => setDialogIsOpen(false)}
          >
            X
          </button>
        </DialogTitle>
        <DialogContent> */}
      {/* <WorkRecordTable
            date={selectedDate}
            addEvent={addEvent}
            deleteEvent={deleteEvent}
          />
        </DialogContent>
      </Dialog> */}
    </div>
  );
};

export default MyCalendar;
