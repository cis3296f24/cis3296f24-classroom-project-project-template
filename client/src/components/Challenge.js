import React, { useState, useEffect, useRef } from 'react';

function Challenge() {
  const initialCards = [
    { id: 1, image: '/images/anime.jpg' },
    { id: 2, image: '/images/anime2.jpg' },
    { id: 3, image: '/images/omen1.jpg' },
    { id: 4, image: '/images/anime.jpg' },
    { id: 5, image: '/images/anime2.jpg' },
    { id: 6, image: '/images/omen1.jpg' },
  ];

  const [cards, setCards] = useState(initialCards);
  const [selectedCard, setSelectedCard] = useState(null);
  const [hoveredCard, setHoveredCard] = useState(null);
  const [visibleCards, setVisibleCards] = useState([]);
  const [isCardMoving, setIsCardMoving] = useState(false);

  const flipSoundRef = useRef(null);

  useEffect(() => {
    flipSoundRef.current = new Audio('/music/flipcard-91468.mp3');

    //this is to put each card down(Peter)
    initialCards.forEach((card, index) => {
      setTimeout(() => {
        setVisibleCards((prevVisibleCards) => [...prevVisibleCards, card.id]);
      }, index * 300); 
    });
  }, []);

  const handleCardClick = (cardId) => {
    if (selectedCard === cardId) {
      setIsCardMoving(true);
      setTimeout(() => {
        setSelectedCard(null);
        setIsCardMoving(false);
      }, 700);
    } else {
      setIsCardMoving(true);
      setSelectedCard(cardId);
      setTimeout(() => {
        setIsCardMoving(false);
      }, 700);
    }

    // card goes to the back of the pile instead of where it was originally (Peter)
    setCards((prevCards) => {
      const clickedCard = prevCards.find((card) => card.id === cardId);
      const otherCards = prevCards.filter((card) => card.id !== cardId);
      return [...otherCards, clickedCard];
    });
  };

  const handleCardFlip = (cardId) => {
    if (selectedCard === cardId) {
      setHoveredCard(cardId);
      if (flipSoundRef.current) {
        flipSoundRef.current.play().catch((error) => {
          console.error('Error playing audio: ', error);
        });
      }
    }
  };

  const handleMouseLeave = () => {
    setHoveredCard(null);
  };

  return (
    <div className="w-full min-h-screen grid place-content-center relative">
      {cards.map((card, index) => (
        visibleCards.includes(card.id) && ( // show the card only when its in the center
          <div
            key={card.id}
            className={`absolute w-[300px] h-[450px] bg-transparent cursor-pointer rounded-3xl perspective-1000 transition-transform duration-700 ease-in-out ${
              selectedCard === card.id && !isCardMoving
                ? 'z-50 left-1/2 top-1/2'
                : `z-${10 - index}`
            }`}
            style={{
              transform:
                selectedCard === card.id && !isCardMoving
                  ? 'translate(-50%, -50%) scale(1)'
                  : `translateX(${index * 20 + 50}px) translateY(-${index * 10}px) scale(0.9)`,
              transition: isCardMoving ? 'transform 0.7s ease-in-out' : 'transform 0.3s ease-in-out',
              position: 'absolute', 
            }}
            onClick={() => handleCardClick(card.id)}
            onMouseEnter={() => handleCardFlip(card.id)}
            onTouchStart={() => handleCardFlip(card.id)} 
            onMouseLeave={handleMouseLeave}
            onTouchEnd={handleMouseLeave} 
          >
            <div
              className={`relative w-full h-full preserve-3d ${
                hoveredCard === card.id ? 'rotate-y-180' : ''
              } duration-500`}
            >
              {/* Front Face of the Card (Peter) */}
              <div className="absolute w-full h-full bg-gradient-to-b from-pink-500 to-blue-500 rounded-3xl shadow-lg flex items-center justify-center backface-hidden">
                <h1 className="text-3xl md:text-4xl lg:text-5xl pixelify-sans-bold text-center text-white">
                  GRIND DAILY
                </h1>
              </div>

              {/* This is when the card is flipped (Peter) */}
              <div className="absolute w-full h-full rounded-3xl overflow-hidden backface-hidden rotate-y-180">
                {/* This is the background image (Peter) */}
                <img
                  src={card.image}
                  className="absolute inset-0 w-full h-full object-cover z-0"
                  alt="Card Image"
                />

                {/* Content Here (Peter) */}
                <div className="relative z-10 p-5 text-neutral-300 space-y-5">
                  <div>
                    <span className="font-bold text-2xl">User ID: {card.id}</span>
                  </div>
                  <div className="flex flex-col space-y-2">
                    <span className="font-semibold">//Title</span>
                    <span className="text-2xl font-bold">//Genre</span>
                    <span>
                      Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's
                      standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a
                      type specimen book.
                    </span>
                  </div>
                  <div className="flex flex-col space-y-5">
                    <span className="font-bold">//description</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )
      ))}
    </div>
  );
}

export default Challenge;
