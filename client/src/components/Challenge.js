import React, { useState, useEffect, useRef } from 'react';

function Challenge() {
  const initialCards = [
    { id: 1, image: '/images/omen1.jpg' },
    { id: 2, image: '/images/anime.jpg' },
    { id: 3, image: '/images/anime2.jpg' },
  ];

  const [cards, setCards] = useState(initialCards);
  const [selectedCard, setSelectedCard] = useState(null);
  const [hoveredCard, setHoveredCard] = useState(null);
  const [visibleCards, setVisibleCards] = useState([]);

  const flipSoundRef = useRef(null);

  useEffect(() => {
    flipSoundRef.current = new Audio('/music/flipcard-91468.mp3');

    // Reveal cards one by one with a delay
    initialCards.forEach((card, index) => {
      setTimeout(() => {
        setVisibleCards((prevVisibleCards) => [...prevVisibleCards, card.id]);
      }, index * 300); // Adjust the delay as needed (e.g., 300ms for each card)
    });
  }, []);

  const handleCardClick = (cardId) => {
    setSelectedCard(cardId);
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
      {/* Stack of Cards */}
      {cards.map((card, index) => (
        visibleCards.includes(card.id) && ( // Show the card only if it's visible
          <div
            key={card.id}
            className={`absolute w-[350px] h-[590px] bg-transparent cursor-pointer rounded-3xl perspective-1000 transition-transform duration-500 ${
              selectedCard === card.id
                ? 'z-50 translate-x-[-50%] translate-y-[-50%] left-1/2 top-1/2'
                : `z-${10 - index} translate-y-[-${index * 15}px]`
            }`}
            style={{
              transform:
                selectedCard === card.id
                  ? 'translate(-50%, -50%) scale(1)'
                  : `translate(-${index * 10}px, -${index * 10}px) scale(0.9)`,
              transition: 'transform 0.5s ease',
              position: selectedCard === card.id ? 'fixed' : 'absolute',
            }}
            onClick={() => handleCardClick(card.id)}
            onMouseEnter={() => handleCardFlip(card.id)}
            onTouchStart={() => handleCardFlip(card.id)} // For mobile support
            onMouseLeave={handleMouseLeave}
            onTouchEnd={handleMouseLeave} // For mobile support
          >
            <div
              className={`relative w-full h-full preserve-3d ${
                hoveredCard === card.id ? 'rotate-y-180' : ''
              } duration-500`}
            >
              <div className="w-full h-full absolute rounded-3xl overflow-hidden">
                <img src={card.image} className="w-full h-full" alt="Card Image" />
              </div>
              <div className="absolute rotate-y-180 w-full h-full bg-[#0F1823] bg-opacity-95 rounded-3xl overflow-hidden p-10 text-neutral-300 space-y-5 backface-hidden">
                <div>
                  <span className="font-bold text-3xl">User ID: {card.id}</span>
                </div>
                <div className="flex flex-col space-y-2">
                  <span className="font-semibold">//Title</span>
                  <span className="text-3xl font-bold">//Genre</span>
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
        )
      ))}
    </div>
  );
}

export default Challenge;
