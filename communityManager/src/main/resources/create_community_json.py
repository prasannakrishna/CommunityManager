import json
import random
import string
from datetime import datetime, timedelta
import uuid

def generate_random_string(length=10):
    """Generates a random alphanumeric string."""
    return ''.join(random.choices(string.ascii_letters + string.digits, k=length))

def generate_random_words(count=3, min_len=4, max_len=8):
    """Generates a list of random words."""
    words = []
    for _ in range(count):
        words.append(generate_random_string(random.randint(min_len, max_len)).lower())
    return words

def generate_random_date(start_year=2020, end_year=2025):
    """Generates a random LocalDateTime string in the format 'YYYY-MM-DDTHH:MM:SS'."""
    start_date = datetime(start_year, 1, 1)
    end_date = datetime(end_year, 12, 31)
    time_diff = end_date - start_date
    random_seconds = random.randint(0, int(time_diff.total_seconds()))
    random_datetime = start_date + timedelta(seconds=random_seconds)
    return random_datetime.strftime("%Y-%m-%dT%H:%M:%S")

def generate_community_record():
    """Generates a single community record based on the CommunityDocument structure."""

    community_types = [
        "LINGUISTIC", "RELIGIOUS", "ETHNIC", "REGIONAL", "SOCIO_CULTURAL", "GEOGRAPHICAL"
    ]
    interest_categories = [
        "DEFAULT", "FOOD_AND_BEVERAGES", "FASHION_AND_LIFESTYLE", "HEALTH_AND_WELLNESS", "BEAUTY_AND_PERSONAL_CARE", "JEWELLERY_AND_ACCESSORIES", "TOYS_GAMES_AND_BABY_PRODUCTS",
        "BOOKS_AND_STATIONERY", "HOME_AND_KITCHEN_ESSENTIALS", "ELECTRONICS_AND_GADGETS", "SPORTS_AND_FITNESS",
        "MEDICINAL_AND_HERBAL_PRODUCTS", "ORGANIC_AND_NATURAL_PRODUCTS","ART_AND_CRAFTS",  "MUSIC_AND_ENTERTAINMENT", "PET_SUPPLIES",
        "GARDENING_AND_OUTDOOR", "DIY_AND_HOBBY", "AUTOMOTIVE_AND_ACCESSORIES", "LOCAL_SPECIALTIES", "CUSTOM"
    ]
    locations = [
        "New York", "London", "Paris", "Tokyo", "Bengaluru", "Berlin", "Sydney", "Dubai", "San Francisco", "Toronto"
    ]

    community_name = f"{random.choice(['Awesome', 'Vibrant', 'Global', 'Local', 'Creative'])} {generate_random_string(5)} Community"
    community_uid = str(uuid.uuid4())
    community_type = random.choice(community_types)
    interest_category = random.choice(interest_categories)
    location = random.choice(locations)
    created_date = generate_random_date()
    updated_date = generate_random_date(start_year=int(created_date.split('-')[0]), end_year=2025)

    # Generate keywords, ensuring they include parts of the community name and interest
    base_keywords = generate_random_words(count=random.randint(3, 7))
    name_parts = community_name.lower().replace("community", "").split()
    keywords = set(base_keywords + name_parts + [interest_category])

    # Generate suggest field
    suggest_inputs = list(keywords) + [community_name]
    suggest_weights = [random.randint(1, 10) for _ in suggest_inputs]

    return {
        "id": str(uuid.uuid4()),
        "communityName": community_name,
        "communityUid": community_uid,
        "type": community_type,
        "interestCategory": interest_category,
        "location": location,
        "createdDate": created_date,
        "updatedDate": updated_date,
        "keywords": list(keywords), # Convert set to list for JSON
        "suggest": {
            "input": suggest_inputs,
            "weight": random.choice(suggest_weights) # Assign a single weight for the whole suggestion
        }
    }

def generate_community_data_payload(num_records=5000):
    """Generates a list of community records."""
    data = []
    for i in range(num_records):
        data.append(generate_community_record())
        if (i + 1) % 500 == 0:
            print(f"Generated {i + 1}/{num_records} records...")
    return data

if __name__ == "__main__":
    num_records_to_generate = 5000
    community_data = generate_community_data_payload(num_records_to_generate)

    # Output to a JSON file
    output_filename = "community_data.json"
    with open(output_filename, "w") as f:
        json.dump(community_data, f, indent=2)

    print(f"\nGenerated {num_records_to_generate} community records and saved to {output_filename}")
