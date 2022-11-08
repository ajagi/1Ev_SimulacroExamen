package com.example.simulacroexamen2;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsetsController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;

public class AdapterProducto extends RecyclerView.Adapter<AdapterProducto.ProductoVH> {

    private List<Producto> objects;
    private int resource;
    private Context context;

    @NonNull
    @Override
    public ProductoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productoFilaView = LayoutInflater.from(context).inflate(resource, null);
        productoFilaView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ProductoVH(productoFilaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoVH holder, int position) {
        Producto producto = objects.get(position);
        holder.lblNombre.setText(producto.getNombre());
        holder.txtCantidad.setText(String.valueOf(producto.getCantidad()));
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // objects.remove(producto);
                // notifyItemRemoved(holder.getAdapterPosition());

                confirmDelete(producto, holder.getAdapterPosition()).show();
            }
        });
        holder.txtCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int cantidad;
                try {
                    cantidad = Integer.parseInt(editable.toString());
                } catch (NumberFormatException ex) {
                    cantidad = 0;
                }
                producto.setCantidad(cantidad);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProducto(producto, holder.getAdapterPosition()).show();
            }
        });
    }

    private android.app.AlertDialog updateProducto(Producto producto, int adapterPosition) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        builder.setTitle("Agregar Producto");
        builder.setCancelable(false);

        View productoAlertView = LayoutInflater.from(context).inflate(R.layout.producto_model_alert, null);
        builder.setView(productoAlertView);

        EditText txtNombre = productoAlertView.findViewById(R.id.txtNombreProductoAlertDialog);
        EditText txtCantidad = productoAlertView.findViewById(R.id.txtCantidadProductoAlertDialog);
        EditText txtPrecio = productoAlertView.findViewById(R.id.txtPrecioProductoAlertDialog);
        TextView lblTotal = productoAlertView.findViewById(R.id.lblTotalProductoAlertDialog);

        txtNombre.setText(producto.getNombre());
        txtCantidad.setText(String.valueOf(producto.getCantidad()));
        txtPrecio.setText(String.valueOf(producto.getPrecio()));

        TextWatcher textWatcher = new TextWatcher() {

            /**
             * Al modifiar un cuadro de texto
             * @param charSequence -> enviar el contenido que habia antes del cambio
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            /**
             * Al modificar un cuadro de texto
             * @param charSequence -> Envia el texto actual despues de la  modificacion
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            /**
             * se dispara el terminar la modificacion
             * @param editable -> Envia el contenido final del cuadro de texto
             */
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int cantidad = Integer.parseInt(txtCantidad.getText().toString());
                    float precio = Float.parseFloat(txtPrecio.getText().toString());
                    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(); //IMPORTANTE MONEDA AUTOMATICA
                    lblTotal.setText(numberFormat.format(cantidad * precio));
                } catch (NumberFormatException ex) {
                }
            }
        };

        txtCantidad.addTextChangedListener(textWatcher);
        txtPrecio.addTextChangedListener(textWatcher);

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtNombre.getText().toString().isEmpty() && !txtCantidad.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()) {
                    producto.setNombre(txtNombre.getText().toString());
                    producto.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                    producto.setPrecio(Float.parseFloat(txtPrecio.getText().toString()));


                    notifyItemChanged(adapterPosition);

                } else {
                    Toast.makeText(context, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog confirmDelete(Producto producto, int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirma Eliminación");
        builder.setCancelable(false);

        builder.setNegativeButton("CANCELAR", null);
        builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(producto);
                notifyItemRemoved(adapterPosition);
            }
        });


        return builder.create();
    }

    public class ProductoVH extends RecyclerView.ViewHolder {

        ImageButton btnEliminar;
        TextView lblNombre;
        EditText txtCantidad;

        public ProductoVH(@NonNull View itemView) {
            super(itemView);
            btnEliminar = itemView.findViewById(R.id.btnEliminarProductoCard);
            lblNombre = itemView.findViewById(R.id.lblNombreProductoCard);
            txtCantidad = itemView.findViewById(R.id.txtCantidadProductoCard);
        }
    }
}