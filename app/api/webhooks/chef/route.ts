import { Webhook } from 'svix'
import { headers } from 'next/headers'
import { WebhookEvent } from '@clerk/nextjs/server'
import { NextResponse } from 'next/server'
import { clerkClient } from "@clerk/clerk-sdk-node";

import { CreateUser } from '@/libs/actions/user.action'

export async function POST(req:Request) {

    // You can find this in the Clerk Dashboard -> Webhooks -> choose the endpoint
  const WEBHOOK_SECRET = process.env.WEBHOOK_SECRET

  if (!WEBHOOK_SECRET) {
    throw new Error('Please add WEBHOOK_SECRET from Clerk Dashboard to .env or .env.local')
  }

  // Get the headers
  const headerPayload = headers()
  const svix_id = (await headerPayload).get('svix-id')
  const svix_timestamp = (await headerPayload).get('svix-timestamp')
  const svix_signature = (await headerPayload).get('svix-signature')

  // If there are no headers, error out
  if (!svix_id || !svix_timestamp || !svix_signature) {
    return new Response('Error occured -- no svix headers', {
      status: 400,
    })
  }

  // Get the body
  const payload = await req.json()
  const body = JSON.stringify(payload)

  // Create a new Svix instance with your secret.
  const wh = new Webhook(WEBHOOK_SECRET)

  let evt: WebhookEvent

  // Verify the payload with the headers
  try {
    evt = wh.verify(body, {
      'svix-id': svix_id,
      'svix-timestamp': svix_timestamp,
      'svix-signature': svix_signature,
    }) as WebhookEvent
  } catch (err) {
    console.error('Error verifying webhook:', err)
    return new Response('Error occured', {
      status: 400,
    })
  }

  // Do something with the payload
  // For this guide, you simply log the payload to the console
  const { id } = evt.data
  const eventType = evt.type

  // CREATE USER in mongodb

  if (eventType == "user.created"){
    const {id,first_name,last_name,email_addresses,username,} = evt.data;

    const user = {
        chefID : id,
        firstName : first_name ?? "Unknown First Name",// default value for MONGO DB
        lastName :last_name ?? "Unknown Last Name",
        email : email_addresses[0].email_address,
        username : username ?? "Unknown Username",
    };

    console.log(user);

    try{
      const newUser = await CreateUser(user);

      if (newUser){
        await clerkClient.users.updateUserMetadata(id,{
          publicMetadata:{
            userId: newUser._id,
          },
        });
      }
      return NextResponse.json( {message: "New user created",user: newUser});
    }catch (error) {
      console.error("Error creating user:", error);
      return new Response('User creation failed', { status: 500 });
    }
  }
  console.log(`Webhook with and ID of ${id} and type of ${eventType}`)
  console.log('Webhook body:', body)
}

