package pagerank;

import java.util.Objects;

public class Node {
    private String id; // Unique identifier (username)
    private String name;
    private String type; // Loại node: "KOL", "Commenter", "Retweeter", etc.

    // Constructor nhận tham số id (username)
    public Node(String id) {
        this.id = id;
        this.name = id; // Hoặc xử lý logic đặt tên khác
        this.type = "Default";
    }

    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}
}
