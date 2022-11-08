package com.example.simulacroexamen2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simulacroexamen2.databinding.ActivityAddProductoBinding;

public class AddProducto extends AppCompatActivity {

    private ActivityAddProductoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnCancelarProductoAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearProductoAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Producto producto = crearProducto();
                if (producto != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.PRODUCTO, producto);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(AddProducto.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Producto crearProducto() {
        if (binding.txtNombreProductoAddProducto.getText().toString().isEmpty() || binding.txtCantidadProductoAddProducto.getText().toString().isEmpty() || binding.txtPrecioProductoAddProducto.getText().toString().isEmpty()){
            return null;
        }

        return new Producto(
                binding.txtNombreProductoAddProducto.getText().toString(),
                Integer.parseInt(binding.txtCantidadProductoAddProducto.getText().toString()),
                Float.parseFloat(binding.txtPrecioProductoAddProducto.getText().toString())
        );
    }
}