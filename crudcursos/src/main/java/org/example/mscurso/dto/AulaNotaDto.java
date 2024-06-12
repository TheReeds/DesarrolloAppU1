package org.example.mscurso.dto;

import org.example.mscurso.entity.Curso;

import java.util.List;

public class AulaNotaDto {
    private AulaDto aula;
    private Curso curso;
    private List<NotaDto> notas;

    // Getters y setters
    public AulaDto getAula() {
        return aula;
    }

    public void setAula(AulaDto aula) {
        this.aula = aula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<NotaDto> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDto> notas) {
        this.notas = notas;
    }
}
