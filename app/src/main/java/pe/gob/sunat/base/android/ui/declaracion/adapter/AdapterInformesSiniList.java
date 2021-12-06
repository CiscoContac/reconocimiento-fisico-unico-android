package pe.gob.sunat.base.android.ui.declaracion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pe.gob.sunat.base.android.R;
import pe.gob.sunat.base.android.model.Contenedor;
import pe.gob.sunat.base.android.model.InformeSini;
import pe.gob.sunat.base.android.util.ItemAnimation;

public class AdapterInformesSiniList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Contenedor> contenedorList;
    private Context context;
    private int animationType = 0;

    public AdapterInformesSiniList(List<Contenedor> contenedorList, Context context, int animationType) {
        this.contenedorList = contenedorList;
        this.context = context;
        this.animationType = animationType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_informessini_list, parent, false);
        viewHolder = new OriginalViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            Contenedor contenedor = contenedorList.get(position);
            view.numContenedor.setText(contenedor.getNumContenedor());
            view.resultado.setText(contenedor.getInformeSini().getResultado());
            view.hipotesis.setText(contenedor.getInformeSini().getHipotesis());
            //Visibilidad del detalle del informe sini
            boolean visible = contenedorList.get(position).isVisible();
            view.detalleLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
            setAnimation(holder.itemView, position);

        }
    }

    @Override
    public int getItemCount() {
        return contenedorList.size();
    }

    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animationType);
            lastPosition = position;
        }
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView numContenedor;
        public TextView resultado;
        public TextView hipotesis;
        public ImageView detalleInformeSini;
        //Detalle visible
        LinearLayout detalleLayout;

        public OriginalViewHolder(View v) {
            super(v);
            numContenedor = (TextView) v.findViewById(R.id.text_view_num_contenedor_sini);
            resultado = (TextView) v.findViewById(R.id.text_view_resultado);
            hipotesis = (TextView) v.findViewById(R.id.text_view_hipotesis);
            detalleInformeSini = (ImageView) v.findViewById(R.id.image_button_deploy_detalle_informesini);
            detalleLayout = (LinearLayout) v.findViewById(R.id.detalleInformeSiniLayout);
            //Visibilidad del detalle del informe sini
            detalleInformeSini.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Contenedor contenedor = contenedorList.get(getAdapterPosition());
                    contenedor.setVisible(!contenedor.isVisible());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
