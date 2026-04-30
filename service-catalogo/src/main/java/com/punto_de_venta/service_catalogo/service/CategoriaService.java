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

    //Crear categoria
    @Transactional
    public Categoria crearCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    //Listar categorias
    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

    //Buscar categoria id
    public Categoria buscarCategoriaId(Long id){
        return categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La categoria no existe"));
    }

    //Actualizar categoria
    @Transactional
    public Categoria actualizarCategoria(Long id, Categoria categoria){
        Categoria categoriaExistente = categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La categoria ingresada no existe"));
        categoriaExistente.setNombre(categoria.getNombre());
        return categoriaRepository.save(categoriaExistente);
    }

    //Eliminar categoria
    @Transactional
    public String eliminarCategoria(Long id){
        Categoria categoria = categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La categoria que desea eliminar no existe"));
        categoriaRepository.delete(categoria);
        return "La categoria ha sido eliminada exitosamente";
    }
}
