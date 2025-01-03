package com.ChangaYa.TP_POOAv.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ChangaYa.TP_POOAv.dto.CategoriaDto;
import com.ChangaYa.TP_POOAv.model.Categoria;
import com.ChangaYa.TP_POOAv.service.CategoriaService;
import com.ChangaYa.TP_POOAv.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImp implements CategoriaService {
    private CategoriaRepository categoriaRepository;

    
    public CategoriaServiceImp(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

	@Override
	public List<CategoriaDto> findAllCategorias() {
		List<Categoria> categorias = categoriaRepository.findAll();  
        return categorias.stream().map((categoria) -> mapTocategoriaDto(categoria)).collect(Collectors.toList());
	}
  
    public CategoriaDto mapTocategoriaDto(Categoria categoria){
        CategoriaDto categoriaDto = CategoriaDto.builder()
        .id(categoria.getId())
        .nombreCategoria(categoria.getNombreCategoria())
        .descripcion(categoria.getDescripcion())
        .build();
        return categoriaDto;
    }
}