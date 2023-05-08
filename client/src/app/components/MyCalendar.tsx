"use client";
import React, { useState } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { Dialog, DialogTitle, DialogContent } from "@material-ui/core";
import WorkRecordTable from "./WorkRecordTable";
import Link from "next/link"

const localizer = momentLocalizer(moment);

const MyCalendar = () => {
  const [dialogIsOpen, setDialogIsOpen] = useState<boolean>(false);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());

  const events = [
    {
      start: new Date(),
      end: new Date(),
      title:`출근:9:00  \n 퇴근:18:00  \n note:xx`,
    },
    {
      start: moment().add(2, "days").toDate(),
      end: moment().add(3, "days").toDate(),
      title: "Another title",
    },
  ];
 /* const updateEvents = (date, startWork, endWork, note) => {
    const updatedEvents = events.filter((event) => {
      return moment(event.start).format("YYYY-MM-DD") !== moment(date).format("YYYY-MM-DD");
    });
  
    const startTime = moment(`${moment(date).format("YYYY-MM-DD")}T${startWork}:00.000`);
    const endTime = moment(`${moment(date).format("YYYY-MM-DD")}T${endWork}:00.000`);
  
    const formattedNote = `출근: ${startWork}, 퇴근: ${endWork}\n${note}`;
  
    updatedEvents.push({
      start: startTime.toDate(),
      end: endTime.toDate(),
      title: formattedNote,
    });
  
    setEvents(updatedEvents);
  };*/

  const handleSelectEvent = (event:any) => {
    setSelectedDate(new Date(moment(event.start).format("YYYY-MM-DD")));
    setDialogIsOpen(true);
  };

  const handleSelectSlot = (slotInfo:any) => {
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
      display: "block",
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
        style={{ height: 600 ,width:1000 }}
        eventPropGetter={eventStyleGetter}
        selectable={true}
        onSelectEvent={handleSelectEvent}
        onSelectSlot={handleSelectSlot}
      />
      <Dialog open={dialogIsOpen} onClose={() => setDialogIsOpen(false)}>
        <DialogTitle className="font-bold">근무 현황 기록표
        <button className="float-right font-bold" onClick={() => setDialogIsOpen(false)} >X</button>
        </DialogTitle>
        <DialogContent>
         <WorkRecordTable date={selectedDate} workRecordId={0} />
        {/* <WorkRecordTable date={selectedDate} updateEvents={updateEvents} />*/}
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default MyCalendar;
