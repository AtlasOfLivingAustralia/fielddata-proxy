package dto

class SpeciesGroupDTO {

    int id
    String name

    List subgroups

    public SpeciesGroupDTO() {
        id = -1
        name = ""
        subgroups = []
    }

    public SpeciesGroupDTO(String name) {
        this()
        this.name = name
    }

    public SpeciesGroupDTO(String name, int id) {
        this(name)
        this.id = id
    }
}
