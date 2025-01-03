package com.ChangaYa.TP_POOAv.serviceTest;

import com.ChangaYa.TP_POOAv.dto.CategoriaDto;
import com.ChangaYa.TP_POOAv.model.Categoria;
import com.ChangaYa.TP_POOAv.repository.CategoriaRepository;
import com.ChangaYa.TP_POOAv.service.impl.CategoriaServiceImp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImp categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCategorias() {
        
        Categoria categoria1 = new Categoria();
        categoria1.setId(new ObjectId());
        categoria1.setNombreCategoria("Categoria 1");
        categoria1.setDescripcion("Descripción de Categoria 1");

        Categoria categoria2 = new Categoria();
        categoria2.setId(new ObjectId());
        categoria2.setNombreCategoria("Categoria 2");
        categoria2.setDescripcion("Descripción de Categoria 2");

        
        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria1, categoria2));

        
        List<CategoriaDto> result = categoriaService.findAllCategorias();

        
        assertEquals(2, result.size());
        assertEquals("Categoria 1", result.get(0).getNombreCategoria());
        assertEquals("Categoria 2", result.get(1).getNombreCategoria());
        assertEquals("Descripción de Categoria 1", result.get(0).getDescripcion());
        assertEquals("Descripción de Categoria 2", result.get(1).getDescripcion());
        
        // Verificar que el repositorio se consultó una vez
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void testMapTocategoriaDto() {
        // Crear una categoría simulada
        Categoria categoria = new Categoria();
        categoria.setId(new ObjectId());
        categoria.setNombreCategoria("Categoria de Prueba");
        categoria.setDescripcion("Descripción de prueba");

        // Llamar al método privado usando el método público
        CategoriaDto result = categoriaService.mapTocategoriaDto(categoria);

        // Verificar los resultados de mapeo
        assertEquals(categoria.getId(), result.getId());
        assertEquals(categoria.getNombreCategoria(), result.getNombreCategoria());
        assertEquals(categoria.getDescripcion(), result.getDescripcion());
    }
}
