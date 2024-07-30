package config

import (
	"time"

	"github.com/go-playground/validator"
	"github.com/kelseyhightower/envconfig"
)

type config struct {
	// Database config
	DbUser    string        `default:"mongodb"`
	DbPass    string        `default:"mongodb"`
	DbHost    string        `default:"localhost"`
	DbPort    uint          `default:"27017"`
	DbName    string        `default:"dbproducts"`
	DbTimeout time.Duration `default:"10s"`
	// Server config
	Host            string        `default:"0.0.0.0"`
	Port            uint          `default:"8080"`
	ShutdownTimeout time.Duration `default:"20s"`
}

var Cfg config
var Valid *validator.Validate

func LoadConfig() error {

	/*err := godotenv.Load()
	if err != nil {
		return err
	}*/

	err := envconfig.Process("APEX", &Cfg)
	if err != nil {
		return err
	}

	Valid = validator.New()

	return nil
}
