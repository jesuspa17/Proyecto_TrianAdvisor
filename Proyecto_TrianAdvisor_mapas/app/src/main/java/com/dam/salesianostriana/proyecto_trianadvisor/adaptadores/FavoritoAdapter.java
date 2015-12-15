package com.dam.salesianostriana.proyecto_trianadvisor.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.dam.salesianostriana.proyecto_trianadvisor.R;

import java.util.ArrayList;

/**
 * @author Jesús Pallares on 12/12/2015.
 * @deprecated Clase que ya no se usa.
 */
public class FavoritoAdapter extends ArrayAdapter<Boolean> {
    private final Context context;
    private final ArrayList<Boolean> values;

    public FavoritoAdapter(Context context, ArrayList<Boolean> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout_fav = inflater.inflate(R.layout.grid_item_fav, parent, false);

        ImageView image_round = (ImageView) layout_fav.findViewById(R.id.imageViewFavItem);

        if((values.get(position))){
            image_round.setImageResource(R.drawable.ic_fav_on);
        }else{
            image_round.setImageResource(R.drawable.ic_fav_off);
        }

        return layout_fav;
    }
}