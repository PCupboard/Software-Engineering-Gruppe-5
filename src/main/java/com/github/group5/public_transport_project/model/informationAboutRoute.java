package public_transport_project.src.main.java.com.github.group5.public_transport_project.model;

public class informationAboutRoute {



        private int id;
        private String name;
        private float distance;

        public informationAboutRoute(int id, String name, float distance) {
            this.id = id;
            this.name = name;
            this.distance = distance;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public float getDistance() {
            return distance;
        }

}
