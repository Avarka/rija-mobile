package hu.vadavar.rija;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hu.vadavar.rija.models.tickets.Ticket;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> implements Filterable {
    private static final String TAG = "TicketAdapter";
    private List<Ticket> tickets;
    private final List<Ticket> ticketsAll;
    private final Context context;

    public TicketAdapter(Context context, List<Ticket> tickets) {
        this.context = context;
        this.tickets = tickets;
        this.ticketsAll = tickets;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "---------- onCreateViewHolder: " + tickets);
        return new TicketViewHolder(
                LayoutInflater.from(context).inflate(R.layout.ticket_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.TicketViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);

        holder.bindTo(ticket);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    @Override
    public Filter getFilter() {
        return statusFilter;
    }

    private final Filter statusFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d(TAG, "performFiltering: " + constraint);

            List<Ticket> filtered = ticketsAll.stream()
                    .filter(ticket -> ticket.getStatus().getName().contentEquals(constraint))
                    .collect(Collectors.toList());
            FilterResults results = new FilterResults();
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            tickets = (List<Ticket>) results.values;
            notifyDataSetChanged();
        }
    };

    public List<Ticket> getFilteredTickets() {
        return tickets.stream()
                .collect(Collectors.toList());
    }

    static public class TicketViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "TicketViewHolder";
        private final TextView ticketTitle;
        private final TextView ticketDescription;
        private final TextView ticketDate;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            ticketTitle = itemView.findViewById(R.id.ticket_title);
            ticketDescription = itemView.findViewById(R.id.ticket_description);
            ticketDate = itemView.findViewById(R.id.ticket_date);
        }

        public void bindTo(Ticket ticket) {
            ticketTitle.setText(ticket.getTitle());
            ticketDescription.setText(ticket.getDescription());
            ticketDate.setText(ticket.getCreated().toString());
        }
    }

}


