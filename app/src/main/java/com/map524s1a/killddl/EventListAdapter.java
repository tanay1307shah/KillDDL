package com.map524s1a.killddl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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


    protected void sendEmail(Event toShare) {
        Log.i("Send email", "");

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        final Intent intent = emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KILLDDL Reminder");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your friend is sharing this event with you.");
        emailIntent.putExtra(Intent.EXTRA_TEXT, toShare.get_eventName());
        emailIntent.putExtra(Intent.EXTRA_TEXT, toShare.get_dueDate());
        emailIntent.putExtra(Intent.EXTRA_TEXT, toShare.get_description());




        try {
            this.mContext.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
          //  Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final EventViewHolder eventViewHolder, int i) {
        final Event e = mEvents.get(i);
      eventViewHolder.titleV.setText(mEvents.get(i).get_eventName());

      eventViewHolder.timeV.setText(e.getTimeStr());
      eventViewHolder.uniqueID.setText(e.get_id());


        if(!e.getColor().isEmpty()){

            eventViewHolder.cv.setCardBackgroundColor(Color.parseColor(e.getColor()));

        }
        else{
            eventViewHolder.cv.setCardBackgroundColor(Color.parseColor("#AFEEEE"));
        }

        eventViewHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(e);
            }
        });

        eventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext.getApplicationContext());
            }
        });
        eventViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            // show details
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext.getApplicationContext());
                final View mView = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.activity_event_view, null);


                String eventNameString;
                String descripString;
                //private Button
                EditText event = mView.findViewById(R.id.eventNameV);
                EditText dueDate = mView.findViewById(R.id.dueDate);
                EditText descrip = mView.findViewById(R.id.descriptionV);

                event.setText(e.get_eventName());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                dueDate.setText(dateFormat.format(e.get_dueDate()));
                descrip.setText(e.get_description());


                final Button editBtn = mView.findViewById(R.id.editBtn);

                //fake remove button, not connected to database
                //use the.childmethod and then call the event getters to get values: String eventName, String description,String timeStr, Date dueDate, Date time, int frequency, int importance, String id,String color
                //use the mview.findbyid to get new values
                //use the firebase updateChildren() function, create map, to update values.
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Event temp = e;
                        //View mView = getLayoutInflater().inflate(R.layout.activity_event_view,null);

                        EditText eventName = mView.findViewById(R.id.eventNameV);
                        EditText description = mView.findViewById(R.id.descriptionV);

                        //eventName.setText(e.get_eventName());
                        //description.setText(e.get_description());

                        String tempEventName = eventName.getText().toString();
                        String tempDescription = description.getText().toString();

                        HashMap<String, Object> updatedValues = new HashMap<String, Object>();
                        updatedValues.put("_eventName", tempEventName);
                        updatedValues.put("_description", tempDescription);

                        //TODO
                        //eventsReference.child(e.get_id()).updateChildren(updatedValues);
                        //TODO close popup
                    }
                });




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
                dialog.show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titleV, timeV, uniqueID;
        Button statusBtn,detailsBtn,delbtn,shareBtn;

        public EventViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card);
            titleV = itemView.findViewById(R.id.evetitle);
            timeV = itemView.findViewById(R.id.timeval);
            uniqueID = itemView.findViewById(R.id.uniqueID);
            statusBtn = itemView.findViewById(R.id.statusBtn);
            shareBtn = itemView.findViewById(R.id.shareBttn);
        }

    }
}
