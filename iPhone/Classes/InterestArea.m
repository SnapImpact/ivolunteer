//
//  InterestArea.m
//  iPhone
//
//  Created by Ryan Schneider on 2/10/09.
//  Copyright 2009 __MyCompanyName__. All rights reserved.
//

#import "InterestArea.h"
#import "iVolunteerData.h"

@implementation InterestArea

+ (NSArray *) loadInterestAreasFromPreferences;
{
	NSMutableArray* selectedInterestAreas_ = (NSMutableArray*) CFPreferencesCopyAppValue((CFStringRef) kInterestAreaKey, 
																						 kCFPreferencesCurrentApplication);
	NSLog(@"Reading in serialized interest areas: %@", selectedInterestAreas_);
	if (!selectedInterestAreas_)
	{
		// return all the interest areas in the system
		return [[[iVolunteerData sharedVolunteerData] interestAreasByName] autorelease];
	}
	
	NSMutableArray* result = [[NSMutableArray alloc] initWithCapacity:[selectedInterestAreas_ count]];
	for (NSArray *interestAreaAsArray in selectedInterestAreas_)
	{
		if (interestAreaAsArray)
		{
			if ([interestAreaAsArray count] == 2)
			{
				InterestArea *anInterestArea = [[InterestArea alloc] initWithId:[interestAreaAsArray objectAtIndex:0] name:[interestAreaAsArray objectAtIndex:1]];
				[result addObject:[anInterestArea autorelease]];
			}
		}
	}
	return [result autorelease];
}

+ (void) saveInterestAreasToPreferences: (NSArray *)interestAreaArray;
{
	NSMutableArray * result = [[NSMutableArray alloc] initWithCapacity:[interestAreaArray count]];
	for (InterestArea * anInterestArea in interestAreaArray)
	{
		NSArray * serializedInterestArea = [[NSArray alloc] initWithObjects:[anInterestArea uid], [anInterestArea name], nil];
		[result addObject:[serializedInterestArea autorelease]];
	}
	NSLog(@"Writing out serialized interest areas: %@",result);
	CFPreferencesSetAppValue((CFStringRef) kInterestAreaKey, result, kCFPreferencesCurrentApplication);

	[result release];	
}

+ (id) interestAreaWithId: (NSString*) uid
                     name: (NSString*) name
{
   InterestArea* i = [InterestArea alloc];
   return [[ i initWithId: uid
                    name: name ] autorelease ];
}

- (id) initWithId: (NSString*) uid_
             name: (NSString*) name_
{
   [super initWithId: uid_ name: name_ ];
   return self;
}

- (void) dealloc {
   [super dealloc];
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
   BEGIN_ENCODER()
   END_ENCODER()
}

- (id)initWithCoder:(NSCoder *)decoder
{
   BEGIN_DECODER()
   END_DECODER()
   return self;
}


@end
