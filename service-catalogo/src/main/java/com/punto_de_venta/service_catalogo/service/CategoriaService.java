package com.punto_de_venta.service_catalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punto_de_venta.service_catalogo.model.Categoria;
import com.punto_de_venta.service_catalogo.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria crearCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoriaId(Long id){
        return categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La categoria no existe"));
    }

    @Transactional
    public Categoria actualizarCategoria(Long id, Categoria categoria){
        Categoria categoriaExistente = categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La categoria ingresada no existe"));
        categoriaExistente.setNombre(categoria.getNombre());
        return categoriaRepository.save(categoriaExistente);
    }

    @Transactional
    public String eliminarCategoria(Long id){
        Categoria categoria = categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La categoria que desea eliminar no existe"));
        categoriaRepository.delete(categoria);
        return "La categoria ha sido eliminada exitosamente";
    }
}
