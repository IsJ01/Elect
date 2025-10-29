from sqlalchemy import Column, Integer, String, Float, Text
from database import Base

class Client(Base):
    __tablename__ = "clients"
    id = Column(Integer, primary_key=True, index=True)
    apartment = Column(String)
    profile = Column(Text)
    services_used = Column(String)
    provider_rating = Column(String)
    provider_interest = Column(Text)
    contact_time = Column(String)
    phone = Column(String)
    fair_price = Column(String)
    note = Column(Text)
    latitude = Column(Float, nullable=True)
    longitude = Column(Float, nullable=True)
