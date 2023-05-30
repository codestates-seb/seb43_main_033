import React, { useState, useEffect } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import { Event as CalendarEvent } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import WorkRecordTable from "../MyWorkPage/WorkRecordTable";

import axios from "axios";

const localizer = momentLocalizer(moment);

interface Event {
  id: number;
  start: Date;
  end: Date;
  title: string;
}

type ApiResponse = {
  company: {
    companyId: number;
    companyName: string;
  }[];
  status: WorkRecord[];
};

type WorkRecord = {
  id: number;
  companyId: number;
  companyName: string;
  memberId: number;
  memberName: string;
  startTime: string;
  finishTime: string;
  note: string;
};

const MyCalendar = () => {
  const [dialogIsOpen, setDialogIsOpen] = useState<boolean>(false);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());
  const [events, setEvents] = useState<Event[]>([]);
  const [currentYear, setCurrentYear] = useState<number>(moment().year());
  const [currentMonth, setCurrentMonth] = useState<number>(moment().month());

  const fetchWorkRecord = () => {
    axios
      .get(
        `${
          process.env.NEXT_PUBLIC_URL
        }/worker/mywork?year=${currentYear}&month=${currentMonth + 1}`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("token"),
          },
        }
      )
      .then((response) => {
        const data: ApiResponse = response.data;
    
        const calendarEvents = data.status.map((item) => ({
          id: item.id,
          title:item.note,
          start: new Date(item.startTime),
          end: new Date(item.finishTime),
        }));

        setEvents(calendarEvents);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
  
    fetchWorkRecord();

  }, [currentYear, currentMonth]);

  const handleNavigate = (date: Date) => {
    setCurrentYear(moment(date).year());
    setCurrentMonth(moment(date).month());
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
      style,
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
        toolbar={true}
        onNavigate={handleNavigate}
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
              <WorkRecordTable date={selectedDate} closeDialog={closeDialog} />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
export default MyCalendar;
