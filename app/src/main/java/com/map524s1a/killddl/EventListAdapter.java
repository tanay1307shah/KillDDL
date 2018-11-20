package com.map524s1a.killddl;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    private List<Event> mEvents;
    private Context mContext;

    public EventListAdapter(Context context, List<Event> events) {
        mContext = context;
        mEvents = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        EventViewHolder evh = new EventViewHolder(v);
        return evh;

    }

    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder eventViewHolder, int i) {
        final Event e = mEvents.get(i);
      eventViewHolder.titleV.setText(mEvents.get(i).get_eventName());

      eventViewHolder.timeV.setText(e.getTimeStr());
      eventViewHolder.uniqueID.setText(e.get_id());





      final int visDet = eventViewHolder.detailsBtn.getVisibility();
      final int visDel = eventViewHolder.delbtn.getVisibility();

        final boolean[] isVisible = {false};

        if(!e.getColor().isEmpty()){
            eventViewHolder.cv.setCardBackgroundColor(Color.parseColor("#"+e.getColor()));

        }
        else{
            eventViewHolder.cv.setCardBackgroundColor(Color.parseColor("#AFEEEE"));
        }



        eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            // show details
            @Override
            public void onClick(View v) {
                if(eventViewHolder.detailsBtn.getVisibility() == View.INVISIBLE && eventViewHolder.delbtn.getVisibility() == View.INVISIBLE) {
                    eventViewHolder.detailsBtn.setVisibility(View.VISIBLE);
                    eventViewHolder.delbtn.setVisibility(View.VISIBLE);
                }
                else{
                    eventViewHolder.detailsBtn.setVisibility(View.INVISIBLE);
                    eventViewHolder.delbtn.setVisibility(View.INVISIBLE);
                }
            }
        });


//      eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
//          @Override
//            public void onClick(View v) {
//
//              if(visDet == View.INVISIBLE && visDel == View.INVISIBLE){
//                  isVisible[0] = true;
//              }else {
//                  isVisible[0] = false;
//              }
//            }
//
//      });
//
//      if(isVisible[0]){
//          eventViewHolder.detailsBtn.setVisibility(View.VISIBLE);
//          eventViewHolder.delbtn.setVisibility(View.VISIBLE);
//      }else{
//          eventViewHolder.detailsBtn.setVisibility(View.INVISIBLE);
//          eventViewHolder.delbtn.setVisibility(View.INVISIBLE);
//      }

      eventViewHolder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // eventsReference.child(e.get_id()).removeValue();


            }
        });

      eventViewHolder.detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View mView = LayoutInflater.from(mContext).inflate(R.layout.activity_event_view, null);

                TextView event = mView.findViewById(R.id.eventNameV);
                TextView dueDate = mView.findViewById(R.id.dueDate);
                TextView descrip = mView.findViewById(R.id.descriptionV);

                event.setText(e.get_eventName());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                dueDate.setText(dateFormat.format(e.get_dueDate()));
                descrip.setText(e.get_description());

                Button removeBtn  = mView.findViewById(R.id.delBtn);
                removeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  eventsReference.child(e.get_id()).removeValue();
                       // Log.e(TAG, " deleted " + e.get_eventName());
                        //TODO close popup
                    }
                });
                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();}
        });
    }



    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titleV, timeV, uniqueID;
        Button statusBtn,detailsBtn,delbtn;

        public EventViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card);
            titleV = itemView.findViewById(R.id.evetitle);
            timeV = itemView.findViewById(R.id.timeval);
            uniqueID = itemView.findViewById(R.id.uniqueID);
            detailsBtn = itemView.findViewById(R.id.detailsBtn);
            delbtn = itemView.findViewById(R.id.deleteBtn);
            statusBtn = itemView.findViewById(R.id.statusBtn);
            //shareBtn = itemView.findViewById(R.id.sharebtn);
            detailsBtn.setVisibility(View.INVISIBLE);
            delbtn.setVisibility(View.INVISIBLE);
        }

    }
}
