package org.example.mscurso.dto;

import java.util.List;

public class NotaBulkUpdateDto {
    private Integer cursoId;
    private List<NotaAlumnoDto> notasAlumnos;

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public List<NotaAlumnoDto> getNotasAlumnos() {
        return notasAlumnos;
    }

    public void setNotasAlumnos(List<NotaAlumnoDto> notasAlumnos) {
        this.notasAlumnos = notasAlumnos;
    }

    public static class NotaAlumnoDto {
        private Integer alumnoId;
        private List<NotaPeriodoDto> notasPeriodos;

        public Integer getAlumnoId() {
            return alumnoId;
        }

        public void setAlumnoId(Integer alumnoId) {
            this.alumnoId = alumnoId;
        }

        public List<NotaPeriodoDto> getNotasPeriodos() {
            return notasPeriodos;
        }

        public void setNotasPeriodos(List<NotaPeriodoDto> notasPeriodos) {
            this.notasPeriodos = notasPeriodos;
        }
    }

    public static class NotaPeriodoDto {
        private Integer periodo;
        private Integer valor;

        public Integer getPeriodo() {
            return periodo;
        }

        public void setPeriodo(Integer periodo) {
            this.periodo = periodo;
        }

        public Integer getValor() {
            return valor;
        }

        public void setValor(Integer valor) {
            this.valor = valor;
        }
    }
}
