import React, { useState } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import { Event as CalendarEvent } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
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
              />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
export default MyCalendar;