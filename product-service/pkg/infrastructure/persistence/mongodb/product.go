package mongodb

import "time"

const (
	productCollection = "products"
)

type DocProduct struct {
	ID          string     `bson:"product_id"`
	Description string     `bson:"description"`
	Price       float64    `bson:"price"`
	Category    string     `bson:"category"`
	CreatedAt   time.Time  `bson:"created_at"`
	UpdatedAt   time.Time  `bson:"updated_at"`
	DeletedAt   *time.Time `json:"deleted_at,omitempty"`
}
