"use client";
import { RecipeResult } from '@/types/RecipeResponseType';
import { fetchRecipeByID } from '@/utils/fetchRecipes';
import { useParams } from 'next/navigation';
import { useEffect, useState, useRef } from 'react';

declare global{
    interface Window {
        YT: any;
        onYouTubeIframeAPIReady: () => void;
    }
}

export default function RecipeDetails() {
    const [recipe, setRecipe] = useState<RecipeResult>()
    const [loading, setLoading] = useState(true)
    const recipeId = useParams().recipeid as string
    const [videoId, setVideoId] = useState<string | null>(null);//yt video ID
    const [playerReady, setPlayerReady] = useState<boolean>(false);//tracks if yt api is ready
    const playerRef = useRef<HTMLDivElement | null>(null);


    console.log(recipeId)

    useEffect(() => {
        //fetch recipe details
        const fetchRecipeDetails = async()=>{
            const recipeResult = await fetchRecipeByID(recipeId);
            setRecipe(recipeResult);
            setLoading(false);
        };
        fetchRecipeDetails();
    }, [recipeId]);


    useEffect(() => {
        const loadYoutubeAPI = () => {
            if (!document.getElementById('youtube-iframe-api')) {
                const script = document.createElement('script');
                script.id = 'youtube-iframe-api';
                script.src = 'https://www.youtube.com/iframe_api';
                document.body.appendChild(script);
            }
        };
        loadYoutubeAPI();
    }, []);

    const fetchVideo = async () => {
        if (!recipe || !recipe.label) return;

        const query = `${recipe.label} recipe`.trim();
        const apiKey = process.env.NEXT_PUBLIC_YOUTUBE_API_KEY;
        const url = `https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&maxResults=1&q=${encodeURIComponent(
            query
        )}&key=${apiKey}`;

        console.log("Line 66) YouTube API Request URL: ", url);
        console.log("Fetching YouTube video for query:", query);

        try {
            const response = await fetch(url);

            if (!response.ok) {
                const errorData = await response.json();
                console.error("Line 73) YouTube API Error:", errorData, "Status Code:", response.status);
                return;
            }

            const data = await response.json();
            console.log("YouTube API Response:", data);

            if (data.items && data.items.length > 0) {
                setVideoId(data.items[0].id.videoId);
            } else {
                setVideoId(null);
            }
        } catch (error) {
            console.error("Error fetching YouTube video:", error);
        }
    };

    useEffect(() => {
        if (recipe){
            fetchVideo();
        }
    }, []);


    const playVideo = () => {
        if (!playerRef.current || !videoId || !playerReady) return;

        //dynamically make the yt player
        new window.YT.Player(playerRef.current, {
            height: '390',
            width: '640',
            videoId: videoId,
            playerVars: {
                playsinline: 1,
            },
        });
    };

    useEffect(() => {
        if (videoId){
            playVideo();
        }
    }, [videoId, playerReady]);


    console.log(recipe)
    if (loading) return <div>RECIPE IS LOADING!!</div>;
    if (!recipe) return <div>No recipe Found</div>;

    return (
        <div style={{color: "black", fontSize: "20px", padding: "20px"}}>
            <img src={recipe.image} alt="Recipe" style={{maxWidth: "100%", borderRadius: "8px"}}/>
            <h1>{recipe.label}</h1>
            <h2>Ingredients</h2>
            <ul>
                {recipe.ingredientLines.map((ingredient, index) => (
                    <li key={index}>{ingredient}</li>
                ))}
            </ul>

            <h2>Source</h2>
            <a href={recipe.url} target="_blank" rel="noopener noreferrer"
               style={{color: "blue", textDecoration: "underline"}}>
                View Full Recipe on {recipe.source}
            </a>

            <h2>Recipe Video</h2>
            <a href={`https://www.youtube.com/watch?v=${videoId}`} target="_blank" rel="noopener noreferrer">
                <iframe
                    width="560"
                    height="315"
                    src={`https://www.youtube.com/embed/${videoId}`}
                    title="YouTube video player"
                    frameBorder="0"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                    allowFullScreen
                ></iframe>
            </a>

        </div>
    );
}

