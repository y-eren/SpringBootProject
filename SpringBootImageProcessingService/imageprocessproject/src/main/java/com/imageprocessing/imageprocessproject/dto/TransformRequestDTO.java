package com.imageprocessing.imageprocessproject.dto;


import lombok.*;

@Getter
@Setter
public class TransformRequestDTO {

    private Transformations transformations;
    @Data
    public static class Transformations {
        private Resize resize;
        private Crop crop;
        private Integer rotate;
        private String format;
        private Filters filters;

        @Data
        public static class Resize {
            private int width;
            private int height;
        }

        @Data
        public static class Crop {
            private int width;
            private int height;
            private int x;
            private int y;
        }

        @Data
        public static class Filters {
            private boolean grayscale;
            private boolean sepia;
        }


    }


}
